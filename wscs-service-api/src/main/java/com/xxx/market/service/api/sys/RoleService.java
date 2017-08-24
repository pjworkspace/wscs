package com.xxx.market.service.api.sys;

import com.jfinal.plugin.activerecord.Page;
import com.xxx.market.service.api.order.OrderException;
import com.xxx.market.service.api.product.*;

import java.util.HashMap;
import java.util.List;

public interface RoleService {
	public void save(RoleSubmitParamDto roleSubmitParamDto) throws ProductException;
	public void update(RoleSubmitParamDto roleSubmitParamDto, Long productId) throws ProductException;
	
	public Page<RoleResultDto> list(RoleSubmitParamDto roleSubmitParamDto);
	
}
