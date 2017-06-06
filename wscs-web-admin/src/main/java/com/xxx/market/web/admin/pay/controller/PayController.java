package com.xxx.market.web.admin.pay.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.xxx.market.model.Purchase;
import com.xxx.market.model.PurchaseOrder;
import com.xxx.market.model.SellerUser;
import com.xxx.market.service.alibaba.AlipayConfig;
import com.xxx.market.service.alibaba.AlipayNotify;
import com.xxx.market.service.alibaba.AlipaySubmit;
import com.xxx.market.service.utils.DateTimeUtil;
import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.BaseController;
import com.jfinal.kit.StrKit;

@RouteBind(path="pay")
public class PayController extends BaseController{

	//正在处理的用户订单
	public static Set<String> userTradeSet = new HashSet<String>();
	
	public void index(){
		List<Purchase> purchases = Purchase.dao.find("select * from " + Purchase.table
				+ " where status = 1");
		setAttr("purchases", purchases);
		render("pay_index.html");
	}
	
	public void create(){
		String payType = getPara("payType", "");
		if(StrKit.isBlank(payType)){
			rendFailedJson("请选择支付类型");
			return;
		}
		
		Long purchaseId = getParaToLong("purchase", -1l);
		if(purchaseId <=0){
			rendFailedJson("请选择购买时长");
			return;
		}
		
		if("AliPay".equals(payType)){
			Purchase purchase = Purchase.dao.findById(purchaseId);
			//支付宝
			PurchaseOrder order = new PurchaseOrder();
			order.setPurchaseId(purchaseId);
			order.setUserId(getSellerId());
			order.setPayType(payType);
			order.setTradeNo(getUUIDStr());
			order.setStatus(0);
			order.setPayFee(purchase.getPayFee() * purchase.getExpiresIn());
			order.setCreated(new Date());
			order.setUpdated(new Date());
			order.save();
			
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.service);
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("seller_id", AlipayConfig.seller_id);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", AlipayConfig.payment_type);
			sParaTemp.put("notify_url", AlipayConfig.notify_url);
			sParaTemp.put("return_url", AlipayConfig.return_url);
			sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
			sParaTemp.put("out_trade_no", order.getTradeNo());
			sParaTemp.put("subject", purchase.getName());
			
			BigDecimal payFee = new BigDecimal(purchase.getPayFee());
			BigDecimal totalFee = payFee.multiply(new BigDecimal(purchase.getExpiresIn()));
			sParaTemp.put("total_fee", totalFee.toString());
			sParaTemp.put("body", purchase.getDesc());
			
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			rendSuccessJson(sHtmlText);
		}else if("WxPay".equals(payType)){
			//微信
			rendSuccessJson();
		}else{
			rendFailedJson("不支持的支付类型");
		}
	}
	
	public void alipayCallBack(){
		System.out.println("=======================pay alipayCallBack!");
		Map<String,String> params = new HashMap<String,String>();
		HttpServletRequest request = getRequest();
		Map<String, String []> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = getPara("out_trade_no");
		//支付宝交易号
		String trade_no = getPara("trade_no");
		//交易状态
		String trade_status = getPara("trade_status");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			synchronized (userTradeSet) {
				if(userTradeSet.contains(out_trade_no)){
					//正在处理的订单数据，本次不处理
					renderText("success");
					return;
				}
				userTradeSet.add(out_trade_no);
			}
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
				PurchaseOrder order = PurchaseOrder.dao.findFirst(" select * from " + PurchaseOrder.table + " where trade_no =　?", out_trade_no.replaceAll("'", ""));
				
				if(order != null && order.getStatus() ==0) {  //表示未支付订单
					order.setStatus(1);		 //表示已支付订单
					order.setAlipayTradeNo(trade_no);
					order.setUpdated(new Date());
					order.update();
					
					Purchase p = Purchase.dao.findById(order.getPurchaseId());
					SellerUser sellerUser = SellerUser.dao.findById(order.getUserId());
					Date startDate = null;
					//获取用户订购信息
					if(sellerUser.getEndDate().after(DateTimeUtil.nowDate())){
						startDate = sellerUser.getEndDate();
					}else{
						startDate = new Date();
					}
					
					//假如用户没有过期再续购
					String endDateStr = DateTimeUtil.getNextDateStringAddDay(DateTimeUtil.toDateString(startDate), p.getExpiresIn() * 30);
					Date endDate = DateTimeUtil.toDate(endDateStr, "00:00:00");
					sellerUser.setEndDate(endDate);
					sellerUser.setUpdated(new Date());
					sellerUser.update();
				}else{
					System.out.println("trade_no:[" + out_trade_no + "]查无此订单");
				}
				
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
			userTradeSet.remove(out_trade_no);
			rendSuccessJson();
		}else{
			rendFailedJson("签名错误");
		}
	}
	
	public void alipayNotify(){
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		HttpServletRequest request = getRequest();
		Map<String, String []> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = getPara("out_trade_no");
		//支付宝交易号
		String trade_no = getPara("trade_no");
		//交易状态
		String trade_status = getPara("trade_status");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if(AlipayNotify.verify(params)){//验证成功
			synchronized (userTradeSet) {
				if(userTradeSet.contains(out_trade_no)){
					//正在处理的订单数据，本次不处理
					renderText("success");
					return;
				}
				userTradeSet.add(out_trade_no);
			}
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
				PurchaseOrder order = PurchaseOrder.dao.findFirst(" select * from " + PurchaseOrder.table + " where trade_no =　?", out_trade_no);
				
				if(order != null && order.getStatus() ==0) {  //表示未支付订单
					order.setStatus(1);		 //表示已支付订单
					order.setAlipayTradeNo(trade_no);
					order.setUpdated(new Date());
					order.update();
					
					Purchase p = Purchase.dao.findById(order.getPurchaseId());
					SellerUser sellerUser = SellerUser.dao.findById(order.getUserId());
					Date startDate = null;
					//获取用户订购信息
					if(sellerUser.getEndDate().after(DateTimeUtil.nowDate())){
						startDate = sellerUser.getEndDate();
					}else{
						startDate = new Date();
					}
					//假如用户没有过期再续购
					String endDateStr = DateTimeUtil.getNextDateStringAddDay(DateTimeUtil.toDateString(startDate), p.getExpiresIn());
					Date endDate = DateTimeUtil.toDate(endDateStr, "00:00:00");
					sellerUser.setEndDate(endDate);
					sellerUser.setUpdated(new Date());
					sellerUser.update();
				}else{
					System.out.println("trade_no:[" + out_trade_no + "]查无此订单");
				}
				
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
			userTradeSet.remove(out_trade_no);
		}else{//验证失败
			System.out.println("签名验证失败.");
		}
		renderText("success");
	}
	
}
