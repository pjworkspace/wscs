package com.xxx.market.web.admin.product.validator;

import com.xxx.market.web.core.validator.BaseJsonRenderValidator;
import com.jfinal.core.Controller;

/**
 * @author jun
 */
public class ProdCategoryValidator extends BaseJsonRenderValidator{

	@Override
	protected void validate(Controller c) {
		validateRequiredString("name", "error_name", "请输入分类名称!");
		validateRequiredString("orders", "error_orders", "请输入分类排序!");		
	}

}
