package com.xxx.market.service.provider;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.xxx.market.model.BuyerUser;
import com.xxx.market.model.Prize;
import com.xxx.market.model.SellerUser;
import com.xxx.market.service.api.prize.ExchangeService;
import com.xxx.market.service.api.prize.ExchangeServiceException;

@Service("flowcashService")
public class FlawcashServiceImpl implements ExchangeService{

	@Override
	public void doExchange(final Prize prize, final BuyerUser buyer, final SellerUser sellerUser, final Map<String, String> params) throws ExchangeServiceException {
		
	}

}
