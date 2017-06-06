package com.xxx.market.service.api.prize;

import java.util.Map;

import com.xxx.market.model.BuyerUser;
import com.xxx.market.model.Prize;
import com.xxx.market.model.SellerUser;

public interface ExchangeService {

	public void doExchange(final Prize prize, final BuyerUser buyerUser, final SellerUser sellerUser, final Map<String, String> params) throws ExchangeServiceException;
}
