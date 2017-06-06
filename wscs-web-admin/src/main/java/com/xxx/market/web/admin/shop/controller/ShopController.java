package com.xxx.market.web.admin.shop.controller;

import java.util.Date;

import com.xxx.market.model.Area;
import com.xxx.market.model.SellerAddr;
import com.xxx.market.model.Shop;
import com.xxx.market.service.api.order.OrderException;
import com.xxx.market.service.api.order.SellerAddrSaveParamDto;
import com.xxx.market.service.api.order.SellerAddrService;
import com.xxx.market.web.core.annotation.RouteBind;
import com.xxx.market.web.core.controller.AdminBaseController;
import com.xxx.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;

@RouteBind(path="shop")
public class ShopController extends AdminBaseController<Shop>{

	@BY_NAME
	private SellerAddrService sellerAddrService;
	
	public void index(){
		Shop shop=Shop.dao.findFirst(" select * from " + Shop.table + " where seller_id=?", getSellerId());
		if(shop!=null){
		setAttr("LogoUrl", getImageDomain()+shop.getShopLogo());
		setAttr("SignUrl", getImageDomain()+shop.getShopSign());
		}
		setAttr("shop", shop);
		render("/shop/shop_index.html");
	}
	
	public void saveShop(){
		final Long shopId=getParaToLong("shopId");
		final String shopName = getPara("shopName");
		final String shopContact = getPara("shopContact");
		final String phone = getPara("phone");
		final String shopLogo = getPara("shopLogo");
		final String shopSign = getPara("shopSign");
		Shop shop=null;
		if(shopId!=null){
			shop=Shop.dao.findById(shopId);
			shop.setUpdated(new Date());
		}else{
			shop=new Shop();
			shop.setSellerId(getSellerId());
			shop.setCreated(new Date());
			shop.setUpdated(new Date());
			shop.setActive(true);
		}
		shop.setShopName(shopName);
		shop.setShopContact(shopContact);
		shop.setShopLogo(shopLogo);
		shop.setShopSign(shopSign);
		shop.setShopContactPhone(phone);
		try{
		if(shop.getId() == null){
			shop.save();	
		}else{
			shop.update();
		}
		    rendSuccessJson(shop);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void sendaddr(){
		SellerAddr sellerAddr = sellerAddrService.getSendAddr(getSellerId());
		if(sellerAddr != null){
			Area area=Area.dao.findById(sellerAddr.getAreaId());
			setAttr("areaPath", area.getTreePath());
		}
		
		setAttr("sendAddr", sellerAddr);
		render("/shop/send_addr.html");
	}
	
	public void saveaddr(){
		final Long areaId=getParaToLong("areaId");
		final String contactName = getPara("contactName");
		final String city = getPara("city");
		final String country = getPara("country");
		final String province = getPara("province");
		final String addr = getPara("addr");
		final String memo = getPara("memo");
		final String phone = getPara("phone");
		final String sellerCompany = getPara("sellerCompany");
		final String zipCode = getPara("zipCode");
		final Long addrId = getParaToLong("addrId");
		
		SellerAddrSaveParamDto addrSaveParamDto = new SellerAddrSaveParamDto(
				getSellerId(), contactName, city, areaId, province, addr, phone);
		if(StrKit.notBlank(country)) addrSaveParamDto.setCountry(country);
		if(StrKit.notBlank(memo)) addrSaveParamDto.setMemo(memo);
		if(StrKit.notBlank(sellerCompany)) addrSaveParamDto.setSellerCompany(sellerCompany);
		if(StrKit.notBlank(zipCode)) addrSaveParamDto.setZipCode(zipCode);
		if(addrId != null) addrSaveParamDto.setAddrId(addrId);
		if(areaId !=null) addrSaveParamDto.setAreaId(areaId);
		try {
			sellerAddrService.saveOrUpdate(addrSaveParamDto);	
			rendSuccessJson(addrSaveParamDto);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
		
	}
	
	@Override
	protected Class<Shop> getModelClass() {
		return Shop.class;
	}

}
