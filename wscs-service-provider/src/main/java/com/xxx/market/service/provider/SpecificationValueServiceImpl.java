package com.xxx.market.service.provider;

import org.springframework.stereotype.Service;

import com.xxx.market.model.SpecificationValue;
import com.xxx.market.service.api.specification.SpecificationValueService;

@Service("specificationValueService")
public class SpecificationValueServiceImpl extends AbstractActivityService implements SpecificationValueService{

	@Override
	public SpecificationValue find(Long SpecificationValueId) {
		return SpecificationValue.dao.findById(SpecificationValueId);
	}

}
