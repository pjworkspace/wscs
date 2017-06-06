/**
 * 文件名:CityController.java
 * 版本信息:1.0
 * 日期:2015-5-30
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.xxx.market.web.mobile.city.controller;

import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.cities.ProvinceDataUtil;
import com.xxx.market.web.core.controller.BaseMobileController;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-30
 */
@RouteBind(path = "city")
public class CityController extends BaseMobileController{

	public void list(){
		String key = getPara("key");
		String type = getPara("type");
		
		if("province".equals(type)){
			String [] cities = ProvinceDataUtil.mCitisDatasMap.get(key);
			rendSuccessJson(cities);
		}else if ("city".equals(type)){
			String [] districts = ProvinceDataUtil.mDistrictDatasMap.get(key);
			rendSuccessJson(districts);
		}
		
	}
	
}
