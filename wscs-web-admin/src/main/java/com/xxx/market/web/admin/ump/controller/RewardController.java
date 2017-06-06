package com.xxx.market.web.admin.ump.controller;

import com.xxx.market.model.FullCut;
import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.AdminBaseController;

@RouteBind(path = "reward")
public class RewardController extends AdminBaseController<FullCut>{
	public void index(){
		render("/promotion/fullCut_index.html");
	}

	@Override
	protected Class<FullCut> getModelClass() {
		return FullCut.class;
	}

}
