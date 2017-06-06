package com.xxx.market.web.admin.ump.controller;

import com.xxx.market.model.Promotion;
import com.xxx.market.service.api.product.ProductException;
import com.xxx.market.service.api.product.ProductParamDto;
import com.xxx.market.service.api.product.ProductResultDto;
import com.xxx.market.service.api.product.ProductService;
import com.xxx.market.service.api.ump.PromotionParamDto;
import com.xxx.market.service.api.ump.PromotionResultDto;
import com.xxx.market.service.api.ump.PromotionService;
import com.xxx.market.service.api.ump.UmpException;
import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.AdminBaseController;
import com.xxx.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.plugin.activerecord.Page;

@RouteBind(path = "promotion")
public class PromotionController extends AdminBaseController<Promotion>{

	@BY_NAME
	private ProductService productService;
	@BY_NAME
	private PromotionService promotionService;
	
	public void index(){
		render("/promotion/promotion_index.html");
	}
	
	public void set(){
		if(getParaToLong("pid") != null){
			setAttr("promotion", promotionService.getPromotionInfo(getParaToLong("pid")));
		}
		render("/promotion/promotion_set.html");
	}
	
	public void listProducts(){
		ProductParamDto productParamDto = new ProductParamDto(getSellerId(), getPageNo());
		try {
			Page<ProductResultDto> pages = productService.getProductsNoPromotionPage(productParamDto);
			rendSuccessJson(pages);
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void list(){
		PromotionParamDto promotionParam = new PromotionParamDto(getSellerId(), getPageNo(), getPageSize());
		try {
			Page<PromotionResultDto> pages = promotionService.list(promotionParam);
			rendSuccessJson(pages);
		} catch (UmpException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void save(){
		try {
			Promotion promotion = getModel();
			String promotionSetItems = getPara("zhekouItems");
			promotionService.save(promotion, getSellerId(), promotionSetItems);
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	@Override
	protected Class<Promotion> getModelClass() {
		return Promotion.class;
	}

}
