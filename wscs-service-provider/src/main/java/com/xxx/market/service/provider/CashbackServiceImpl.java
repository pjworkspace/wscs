package com.xxx.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxx.market.model.Cashback;
import com.xxx.market.model.CashbackSet;
import com.xxx.market.model.Product;
import com.xxx.market.service.api.product.ProductException;
import com.xxx.market.service.api.product.ProductParamDto;
import com.xxx.market.service.api.product.ProductResultDto;
import com.xxx.market.service.api.ump.CashbackResultDto;
import com.xxx.market.service.api.ump.CashbackService;
import com.xxx.market.service.api.ump.PromotionParamDto;
import com.xxx.market.service.api.ump.UmpException;
import com.xxx.market.service.base.AbstractServiceImpl;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

@Service("cashbackService")
public class CashbackServiceImpl extends AbstractServiceImpl implements CashbackService{

	@Override
	@Transactional(rollbackFor = UmpException.class)
	public void save(Cashback cashback, Long sellerId) throws UmpException{
		if(cashback == null || sellerId == null)
			throw new UmpException("保存订单返现出错:参数不全");
		
		if(cashback.getId() == null){
			cashback.setSellerId(sellerId);
			cashback.setActive(true);
			cashback.save();
			String productids=cashback.getProductIds();
			String productIds[]=(productids.substring(0,productids.length()-1)).split("-");
			for (int i = 0; i < productIds.length; i++) {
				CashbackSet cashbackSet=new CashbackSet();
				cashbackSet.setCashbackId(cashback.getId());
				cashbackSet.setProductId(new Long(productIds[i]));
				try {
					cashbackSet.save();
				} catch (Exception e) {
					throw new UmpException(e.getMessage());
				}
			}
		}else{
			cashback.update();
			try {
				Db.deleteById(CashbackSet.table,"cashback_id",cashback.getId());
			} catch (Exception e) {
				throw new UmpException(e.getMessage());
			}
			
			String productids=cashback.getProductIds();
			String productIds[]=(productids.substring(0,productids.length()-1)).split("-");
			for (int i = 0; i < productIds.length; i++) {
				CashbackSet cashbackSet=new CashbackSet();
				cashbackSet.setCashbackId(cashback.getId());
				cashbackSet.setProductId(new Long(productIds[i]));
				try {
					cashbackSet.save();
				} catch (Exception e) {
					throw new UmpException(e.getMessage());
				}
			}
		}
	}

	@Override
	public Page<CashbackResultDto> list(PromotionParamDto promotionParam) throws UmpException {
		if(promotionParam == null || promotionParam.getSellerId() == null)
			throw new UmpException("获取限时打折列表数据参数错误");
		Page<Cashback> pages = Cashback.dao.paginate(promotionParam.getPageNo(), promotionParam.getPageSize(),
				"select * ", 
				" from " + Cashback.table + " where seller_id=? ", promotionParam.getSellerId());
		List<CashbackResultDto> cashbackDtos = new ArrayList<CashbackResultDto>();
		
		for (Cashback cashback : pages.getList()) {
			CashbackResultDto dto=new CashbackResultDto();
			dto.setId(cashback.getId());
			dto.setName(cashback.getName());
			dto.setStartDate(cashback.getStartTime());
			dto.setEndDate(cashback.getEndTime());
			dto.setCashbackStart(cashback.getCashbackStart().toString());
			if(cashback.getCashbackMethod()==0){
				dto.setCashbackMethod("随机返现");
				dto.setCashbackStart(cashback.getCashbackStart().toString()+"% 至  "+cashback.getCashbackEnd().toString()+"%");
			}else{
				dto.setCashbackMethod("固定返现");
				dto.setCashbackStart(cashback.getCashbackStart().toString()+"%");
			}
			dto.setCashbackLimit(cashback.getCashbackLimit());
			cashbackDtos.add(dto);
		}
		return  new Page<CashbackResultDto> (cashbackDtos, promotionParam.getPageNo(), promotionParam.getPageSize(), pages.getTotalPage(), pages.getTotalRow());
	}

	@Override
	public Page<ProductResultDto> getProducts4CashbackPage(ProductParamDto productParamDto) throws ProductException {
		if(productParamDto == null || productParamDto.getSellerId() == null) throw new ProductException("获取未打折商品列表出错");
		List<ProductResultDto> resultDtos = new ArrayList<ProductResultDto>();
		
		//查询出有效打折数据
		Date currDate = new Date();
		final String select = "select * ";
		final String sqlExceptSelect = " from "+Product.table+" where seller_id=? and is_marketable=1 and active=1 and id not in"
				+ " (select product_id from " + CashbackSet.table + " where cashback_id "
				+ "in (select id from "+Cashback.table+" where seller_id=? and start_time<=? and end_time >=? ))";
		Page<Product> pages = Product.dao.paginate(productParamDto.getPageNo(), productParamDto.getPageSize(),
				select, sqlExceptSelect,
				productParamDto.getSellerId(), productParamDto.getSellerId(), currDate, currDate);
		for(Product product : pages.getList()){
			ProductResultDto resultDto = new ProductResultDto();
			resultDto.setId(product.getId());
			resultDto.setImg(getImageDomain() + product.getImage());
			resultDto.setName(product.getName());
			resultDto.setPrice(product.getPrice());
			resultDto.setStock(product.getStock());
			resultDtos.add(resultDto);
		}
	
		return new Page<ProductResultDto>(resultDtos, pages.getPageNumber(), pages.getPageSize(), pages.getTotalPage(), pages.getTotalRow());
	}

}
