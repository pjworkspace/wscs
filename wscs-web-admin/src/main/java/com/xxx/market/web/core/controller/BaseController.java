
package com.xxx.market.web.core.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xxx.market.model.AuthUser;
import com.xxx.market.model.CompTicket;
import com.xxx.market.model.SellerUser;
import com.xxx.market.service.utils.ResultUtil;
import com.xxx.market.web.core.plugin.spring.IocInterceptor;
import com.xxx.market.web.core.weixin.ApiController;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.render.JsonRender;
import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.kit.WxSdkPropKit;


@Before({IocInterceptor.class})
public abstract class BaseController extends ApiController {
	protected static final String CACHENAME_COMP_TIKET = "comp_tiket_cache";
	protected static final String CACHENAME_COMP_TIKET_KEY = "key_comp_tiket";
	public Logger log = Logger.getLogger(getClass());
	protected final static String NET_ERROR_MSG = "请求接口失败，请检查网络，或者刷新重连";
	protected int getPageNo(){
		return this.getParaToInt("page", 1);
	}
	protected int getPageSize(){
		return this.getParaToInt("rows", 10);
	}
	
	/**token超时时间600s*/
	protected int tokenTout=600; 
	
	/**
     * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
     * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
     */
    @Override
    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken(WxSdkPropKit.get("token"));
        ac.setAppId(WxSdkPropKit.get("wx_app_id"));
        ac.setAppSecret(WxSdkPropKit.get("wx_app_secret"));

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(WxSdkPropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(WxSdkPropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }
	
	protected Subject getSubject() {
	    return SecurityUtils.getSubject();
	}
	
	public SellerUser getSellerUser(){
		Object principal =  getSubject().getPrincipal(); 
		return principal !=null && principal instanceof SellerUser ? (SellerUser) principal : null;
	}
	
	protected Long getSellerId(){
		return getSellerUser().getId();
	}
	
	/**
	 * HTML视图
	 * @param view 视图文件名不含.html
	 */
	protected void renderHTML(String view) {
		if(view.endsWith(".html")){
			super.render(view);
		}else{
			super.render(view+".html");
		}
	}

	/***
	 * 
	 * @param success
	 * @param statusCode 状态码默认为401
	 * @param msg
	 * @param obj 数组 [0]:data [1]:tokenid
	 */
	protected void rendJson(boolean success, Integer statusCode, String msg, Object... data){
		Map<String,Object>json=new HashMap<String,Object>();
		json.put("Success",success);
		json.put("status",success?200:(statusCode==null?401:statusCode));
		json.put("msg",msg);
		if(data!=null&&data.length>0){
			json.put("data",data[0]);
			if(data.length>1){
				json.put("tokenid",data[1]);
			}
		}
		rendJson(json);
	}
	
	protected void rendSuccessJson(Object data){
		rendJson(ResultUtil.genSuccessResult(data));
	}
	
	protected void rendSuccessJson(){
		rendJson(ResultUtil.genSuccessResult());
	}
	
	protected void rendFailedJsonObj(final Object obj){
		rendJson(ResultUtil.genFailedResultList(obj));
	}
	
	protected void rendFailedJson(final String msg){
		rendJson(ResultUtil.genFailedResult(msg));
	}
	
	protected void rendFailedJson(final int code, final String msg){
		rendJson(ResultUtil.genFailedResult(code, msg));
	}
	
	protected void rendFailedJson(final String code, final String msg){
		rendJson(ResultUtil.genFailedResult(code, msg));
	}
	
	protected void rendJson(Object json){
		String agent = getRequest().getHeader("User-Agent");
		if(agent!=null && agent.contains("MSIE"))
			this.render(new JsonRender(json).forIE());
		else{
			this.render(new JsonRender(json));
		}
	}
	
	protected String inputStream2String(InputStream in) throws UnsupportedEncodingException, IOException{
		if(in == null)
			return "";
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}
	
	protected String getCompTicket(){
		CompTicket compTicket = CompTicket.dao.findFirstByCache(CACHENAME_COMP_TIKET, CACHENAME_COMP_TIKET_KEY, "select * from " + CompTicket.table);
		return compTicket == null ? "" : compTicket.getCompVerifyTicket();
	}
	
	/**
	 * 获取当前使用的公众账号
	 * @return
	 */
	protected AuthUser getUsedAuthUser(){
		return AuthUser.dao.findFirst("select * from " + AuthUser.table + " where seller_id=? and is_used=? and active=1", getSellerId(), true);
	}
	
	protected synchronized String getUUIDStr(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	protected Long getModuleId(){
		return getParaToLong("module_id");
	}
	
	protected String getImageDomain(){
		return PropKit.get("img_domain");
	}
	
}
