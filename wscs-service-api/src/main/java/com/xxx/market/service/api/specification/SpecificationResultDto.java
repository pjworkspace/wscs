package com.xxx.market.service.api.specification;

import java.util.List;

import com.xxx.market.model.Specification;
import com.xxx.market.model.SpecificationValue;
import com.xxx.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class SpecificationResultDto extends AbstractResultDto{

	private Specification specification;
	private List<SpecificationValue> specificationValues;
	public Specification getSpecification() {
		return specification;
	}
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	
}
