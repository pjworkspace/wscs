package com.xxx.market.web.mobile.qrcode.controller;

import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.BaseMobileController;
import com.xxx.market.web.core.render.QrcodeRender;

@RouteBind(path="qrcode")
public class QrcodeController extends BaseMobileController{
	
	//生成二维码图片流
	public void genio(){
		final String content = getPara("url");
	    render(new QrcodeRender(content));
	}
	
}
