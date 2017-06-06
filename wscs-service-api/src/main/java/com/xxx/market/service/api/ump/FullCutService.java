package com.xxx.market.service.api.ump;

import java.util.List;

import com.xxx.market.model.FullCut;
import com.xxx.market.model.Product;
import com.xxx.market.service.api.product.ProductException;
import com.xxx.market.service.api.product.ProductFullCutResultDto;
import com.xxx.market.service.api.product.ProductParamDto;
import com.xxx.market.service.api.product.ProductResultDto;
import com.jfinal.plugin.activerecord.Page;

/**
 * 满减送
 * @author drs
 *
 */
public interface FullCutService {
	public FullCut save(FullCutParamDto paramDto)throws UmpException;
	
	public Page<ProductResultDto> getProducts4FullCutPage(ProductParamDto productParamDto) throws ProductException;
    
	public Page<FullCut> list(PromotionParamDto promotionParam) throws UmpException;

	public List<ProductFullCutResultDto> getProductFullCut(Product product) throws UmpException;
}
