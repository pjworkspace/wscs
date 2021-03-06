package com.xxx.market.web.admin.module.controller;

import com.xxx.market.service.api.module.FuncResultDto;
import com.xxx.market.service.api.module.GetFunParamDto;
import com.xxx.market.service.api.module.ModuleException;
import com.xxx.market.service.api.module.ModuleService;
import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.BaseController;
import com.xxx.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path="module")
public class ModuleController extends BaseController{

	@BY_NAME
	private ModuleService moduleService;
	
	public void func(){
		Long funId = getParaToLong("fun_id");
		String authAppId = getPara("auth_app_id");
		Long moduleId = getParaToLong("module_id");
		
		GetFunParamDto getFunParamDto = new GetFunParamDto(funId, authAppId, moduleId);
		try {
			FuncResultDto funcResultDto = moduleService.getModuleFun(getFunParamDto);
			rendSuccessJson(funcResultDto);
		} catch (ModuleException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
}
