package com.xxx.market.web.admin.product.controller;

import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.BaseController;
@RouteBind(path = "component", viewPath = "component")
public class ComponentController extends BaseController{
     public void index(){
    	 render("/component/component_set.html");
     }
}
