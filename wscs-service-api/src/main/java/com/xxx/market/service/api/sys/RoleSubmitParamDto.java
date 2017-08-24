package com.xxx.market.service.api.sys;

import com.xxx.market.service.common.AbstractParamDto;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class RoleSubmitParamDto extends AbstractParamDto{

	private String name;
	private String value;
	private String description;
	private Integer isSystem;
	public RoleSubmitParamDto(String name, String value,
                              String description,Integer isSystem) {
		super();
		this.name = name;
		this.value = value;
		this.description = description;
		this.name = name;
		this.isSystem = isSystem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}
