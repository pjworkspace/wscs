package com.xxx.market.service.api.ump;

import com.xxx.market.model.Cashback;
import com.xxx.market.service.api.product.ProductException;
import com.xxx.market.service.api.product.ProductParamDto;
import com.xxx.market.service.api.product.ProductResultDto;
import com.jfinal.plugin.activerecord.Page;

/**
 * 订单返现
 * @author
 *
 */
public interface CashbackService {
	
	
	public void save(Cashback cashback,Long sellerId) throws UmpException;
	
	public Page<CashbackResultDto> list(PromotionParamDto promotionParam) throws UmpException;
	
	public Page<ProductResultDto> getProducts4CashbackPage(ProductParamDto productParamDto) throws ProductException;
}
