package com.xxx.market.service.api.delivery;

import com.xxx.market.model.DeliveryTemplate;
import com.xxx.market.model.SellerUser;

public interface DeliveryTemplateService {
	
  public DeliveryTemplate doSave(DeliveryTemplate dt,String items, SellerUser sellerUser) throws DeliveryTemplateException;
  
  public DeliveryTemplate doUpdate(DeliveryTemplate dt,String items, SellerUser sellerUser) throws DeliveryTemplateException;

}
