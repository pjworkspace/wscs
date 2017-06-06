package com.xxx.market.service.provider;

import org.springframework.stereotype.Service;

import com.xxx.market.model.DeliveryTemplate;
import com.xxx.market.model.SellerUser;
import com.xxx.market.service.api.delivery.DeliveryTemplateException;
import com.xxx.market.service.api.delivery.DeliveryTemplateService;
@Service("deliveryTemplateService")
public class DeliveryTemplateServiceImpl implements DeliveryTemplateService{

	@Override
	public DeliveryTemplate doSave(DeliveryTemplate dt, String items, SellerUser sellerUser)
			throws DeliveryTemplateException {
		return null;
	}

	@Override
	public DeliveryTemplate doUpdate(DeliveryTemplate dt, String items, SellerUser sellerUser)
			throws DeliveryTemplateException {
		return null;
	}

}
