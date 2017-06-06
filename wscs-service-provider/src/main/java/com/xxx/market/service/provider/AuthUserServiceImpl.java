package com.xxx.market.service.provider;

import org.springframework.stereotype.Service;

import com.xxx.market.model.AuthUser;
import com.xxx.market.service.api.authuser.AuthUserService;
import com.xxx.market.service.api.exception.MarketBaseException;
import com.xxx.market.service.base.AbstractServiceImpl;

@Service("authUserService")
public class AuthUserServiceImpl extends AbstractServiceImpl implements AuthUserService{

	@Override
	public AuthUser getUsedAuthUser(Long sellerId) throws MarketBaseException {
		if(sellerId == null) throw new MarketBaseException("请传入参数");
		AuthUser authUser = AuthUser.dao.findFirst("select * from " + AuthUser.table + " where seller_id=? and is_used=1 and active=1", sellerId);
		if(authUser == null) throw new MarketBaseException("授权公众号不存在");
		return authUser;
	}

}
