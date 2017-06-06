package com.xxx.market.service.provider;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xxx.market.model.Agent;
import com.xxx.market.model.AgentCommission;
import com.xxx.market.model.AuthUser;
import com.xxx.market.model.BuyerRecharge;
import com.xxx.market.model.BuyerUser;
import com.xxx.market.model.Order;
import com.xxx.market.model.OrderItem;
import com.xxx.market.model.Product;
import com.xxx.market.service.api.agent.AgentException;
import com.xxx.market.service.api.agent.AgentService;
import com.xxx.market.service.api.authuser.AuthUserService;
import com.xxx.market.service.api.pay.PayException;
import com.xxx.market.service.api.pay.PayService;
import com.xxx.market.service.base.AbstractServiceImpl;
import com.xxx.market.service.enmu.PaymentStatus;
import com.xxx.market.service.enmu.ShippingStatus;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.TemplateData;
import com.weixin.sdk.api.TemplateMsgApi;
import com.weixin.sdk.kit.WxSdkPropKit;
import com.weixin.sdk.pay.UnifiedOrderApi;
import com.weixin.sdk.pay.UnifiedOrderReqData;
import com.weixin.sdk.pay.UnifiedOrderResData;
import com.weixin.sdk.utils.SignKit;

@Service("payService")
public class PayServiceImpl extends AbstractServiceImpl  implements PayService{
	
	@Autowired
	private AuthUserService authUserService;
	@Autowired
	private AgentService agentService;

	@Override
	@Transactional(rollbackFor = PayException.class)
	public void resultLotteryCallback(BuyerUser user, TreeMap<String, Object> params) throws PayException {
		//更新用户抽奖次数
		String totalFee = (String) params.get("total_fee");
		String tradeNo = (String) params.get("out_trade_no");
		tradeNo = tradeNo.replaceAll("'", "");
		String transactionId = (String) params.get("transaction_id");
		if("200".equals(totalFee)){
			user.setScore(user.getScore() + 20);
		}else if("500".equals(totalFee)){
			user.setScore(user.getScore() + 60);
		}else if("1000".equals(totalFee)){
			user.setScore(user.getScore() + 120);
		}else{
			throw new PayException("支付金额错误");
		}
		
		user.setUpdated(new Date());
		if(!user.update()) throw new PayException("更新公众号["+user.getAuthAppId()+"]用户["+user.getId()+"]信息失败");
		//记录用户充值记录
		BuyerRecharge recharge = new BuyerRecharge();
		recharge.setBuyerUserId(user.getId());
		recharge.setRecharge(totalFee);
		recharge.setOutTradeId(tradeNo);
		recharge.setTransactionId(transactionId);
		recharge.setStatus(1);
		recharge.setCreated(new Date());
		recharge.setUpdated(new Date());
		recharge.save();	
	}


	@Override
	public TreeMap<String, Object> prepareToPay(String openId, String tradeNo, BigDecimal payFee,
			String desc, String ip) throws PayException {
		String payFee_ = String.valueOf(payFee.multiply(new BigDecimal(100)).intValue());
		
		UnifiedOrderResData unifiedOrderResData = null;
		UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(openId, desc, tradeNo, payFee_,  ip, "JSAPI");
		
		try {
			UnifiedOrderApi unifiedOrderApi = new UnifiedOrderApi();
			unifiedOrderResData = (UnifiedOrderResData) unifiedOrderApi.post(unifiedOrderReqData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException(e.getMessage());
		}
		
		if(unifiedOrderResData==null || !"OK".equals(unifiedOrderResData.getReturn_msg())){
			if(unifiedOrderResData != null){
				System.out.println(unifiedOrderResData.getReturn_msg());
			}
			throw new PayException("调用微信统一下单接口失败");
		}
		
		//准备调用支付js接口的参数
		TreeMap<String, Object> params = new TreeMap<String, Object>();
    	params.put("appId", WxSdkPropKit.get("wx_app_id"));
        params.put("timeStamp", Long.toString(new Date().getTime()));
        params.put("nonceStr", SignKit.genRandomString32());
        params.put("package", "prepay_id="+unifiedOrderResData.getPrepay_id());
        params.put("signType", "MD5");
        
        String paySign = SignKit.sign(params);
        
        params.put("paySign", paySign);
        params.put("packageValue", "prepay_id="+unifiedOrderResData.getPrepay_id());
        params.put("returnMsg", unifiedOrderResData.getReturn_msg());
        params.put("sendUrl", WxSdkPropKit.get("wx_notify_url"));
        params.put("tradeno", tradeNo);
		return params;
	}

	@Override
	public void resultOrderCallback(BuyerUser user, TreeMap<String, Object> params) throws PayException {
		String tradeNo = (String) params.get("out_trade_no");
		String transactionId = (String) params.get("transaction_id");
		Order order = Order.dao.findFirst("select * from "+Order.table+" where trade_no = ?", tradeNo);
		if(order != null){
			order.setPaymentStatus(PaymentStatus.paid.ordinal());
			order.setShippingStatus(ShippingStatus.unshipped.ordinal());
			order.setTransactionId(transactionId);
			order.setUpdated(new Date());
			order.update();
			
			//更新商品库存
			List<OrderItem> orderItems = OrderItem.dao.find("select * from " + OrderItem.table + " where order_id=? ", order.getId());
			for(OrderItem orderItem : orderItems){
				Product product = Product.dao.findById(orderItem.getProductId());
				product.setStock(product.getStock() - orderItem.getQuantity() > 0 ? product.getStock() - orderItem.getQuantity() : 0);
				product.setSales(product.getSales() + orderItem.getQuantity());
				product.update();
			}
			//检查商品是否有订单返现的活动
			
			//分销处理
			//1.检查该用户是否有上级分销商
			//2.根据分销规则给上级分销商分佣
			Agent agent = Agent.dao.findFirst("select * from " + Agent.table + " where buyer_id=? ", user.getId());
			if(agent != null){
				List<Agent> agents = agentService.getSelfAndParent(agent.getId());
				for(Agent ag : agents){
					try {
						BigDecimal yongjin = agentService.getAgentCommission(ag, order);
						//记录分销商获得的佣金
						AgentCommission agentCommission = new AgentCommission();
						agentCommission.setAgentId(ag.getId());
						agentCommission.setOrderId(order.getId());
						agentCommission.setCommissionValue(yongjin);
						agentCommission.setActive(true);
						agentCommission.setCreated(new Date());
						agentCommission.setUpdated(new Date());
						agentCommission.save();
					} catch (AgentException e) {
						continue;
					}
				}
			}
			
			new SendTemplateMsgThread (user, order).start();
		}
		
	}
	
	class SendTemplateMsgThread extends Thread{
		private BuyerUser buyerUser;   //买家
		private Order order;
		public SendTemplateMsgThread(BuyerUser buyerUser, Order order){
			this.buyerUser = buyerUser;
			this.order = order;
		}
		
		@Override
		public void run() {
			super.run();
			if (buyerUser == null) return;
			//推送一条消息给公众号消息接收者，提醒有人下单了
			AuthUser authUser = authUserService.getUsedAuthUser(buyerUser.getSellerId());
			if(authUser ==null) return;
			JSONObject json = new JSONObject();
			json.put("template_id_short", "TM00015");
			String templateId = TemplateMsgApi.getTemplateId(json.toString());
			if(StrKit.isBlank(templateId)) return;
			
			StringBuffer sbuffer = new StringBuffer();
			List<OrderItem> orderItems = OrderItem.dao.find("select * from " + OrderItem.table + " where order_id=? ", order.getId());
			for(OrderItem orderItem : orderItems){
				sbuffer.append(orderItem.getName() + "X" + orderItem.getQuantity()).append("<br>");
			}
			//1查找消息接收者
			List<BuyerUser> receivers = BuyerUser.dao.find(
					"select * from " + BuyerUser.table + " where seller_id=? and auth_app_id=? and subscribe=1 and is_receiver=1", 
					buyerUser.getSellerId(), authUser.getAppId());
			//给公众号管理者发送消息，有人支付订单
			for(BuyerUser receiver : receivers){
				TemplateData templateData = TemplateData.New().setTemplate_id(templateId)
						.setTouser(receiver.getOpenId())
						.setUrl("http://"+PropKit.get("server_domain")+"/order/detail/"+order.getId())
						.add("first",  "客户[" + buyerUser.getNickname() + "]已成功支付货款，请准备发货", "#173177")
						.add("orderProductName", sbuffer.toString(), "#173177")
						.add("orderMoneySum", order.getTotalPrice() + "，邮费：" + order.getPostFee(), "#173177")
						.add("backupFieldName", "", "#173177")
						.add("backupFieldData", "", "#173177")
						.add("remark", "订单编号："+order.getOrderSn()+", 如对订单有疑问，请联系公众号客服！", "#173177");
					
					ApiResult apiResult = TemplateMsgApi.send(templateData.build());
					
					if(!apiResult.isSucceed()){
						logger.error("error_code:" + apiResult.getErrorCode() + ",error_msg" + apiResult.getErrorMsg());
					}
			}
			
		}
		
	}

}
