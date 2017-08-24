package com.yaolin.generator.impl.with.freemarker.dto;

import com.yaolin.generator.api.Table;
import com.yaolin.generator.impl.with.freemarker.utils.StringUtils;

/**
 * 
 * @author yaoin
 */
public class TableDTO {

	private String name;
	private String module;
	private String modelName;
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static TableDTO toDTO(Table table) {
		if (table == null) {
			return null;
		}
		TableDTO dto = new TableDTO();
		dto.setName(table.getName());
		dto.setRemark(table.getRemark());
		String module = "";
		if (table.getName() != null && table.getName().indexOf("_") > 0) {
			module = table.getName().substring(0, table.getName().indexOf("_"));
		}
		dto.setModule(module);
		String modelName = "";
		if (table.getName() != null) {
			String tableWithoutModule = table.getName().replace(module, "");
			String[] item = tableWithoutModule.split("_");
			for (String s : item) {
				if (s.trim().length() != 0) {
					modelName += StringUtils.firstCharUpper(s);
				}
			}
		}
		dto.setModelName(modelName);
		return dto;
	}
}
