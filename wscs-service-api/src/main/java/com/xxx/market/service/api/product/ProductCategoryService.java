package com.xxx.market.service.api.product;

import java.util.List;

import com.xxx.market.model.ProductCategory;

/**
 * 
 * @author drs
 *  商品分类
 */
public interface ProductCategoryService {
	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	public List<ProductCategory> findRoots();
	
	
	public ProductCategory find(Long productCategoryId);
}
