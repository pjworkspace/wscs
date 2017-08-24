package com.yaolin.generator.impl.with.freemarker.dto;

import java.util.ArrayList;
import java.util.List;

import com.yaolin.generator.api.Column;
import com.yaolin.generator.impl.with.freemarker.utils.StringUtils;

/**
 * 
 * @author yaoin
 */
public class ColumnDTO {

	private String name;
	private String fieldName;
	private boolean primaryKey;
	private boolean autoIncrement;
	private boolean nullable;
	private Object defaultValue;
	private String remark;
	private String javaType;

	private TableDTO tableDTO;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public TableDTO getTableDTO() {
		return tableDTO;
	}

	public void setTableDTO(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
	}

	public static ColumnDTO toDTO(Column column) {
		if (column == null) {
			return null;
		}
		ColumnDTO dto = new ColumnDTO();
		dto.setName(column.getName());
		dto.setAutoIncrement(column.isAutoIncrement());
		dto.setNullable(column.isNullable());
		dto.setPrimaryKey(column.isPrimaryKey());
		dto.setDefaultValue(column.getDefaultValue());
		dto.setRemark(column.getRemark());
		dto.setTableDTO(TableDTO.toDTO(column.getTable()));
		String javaType = new TypeMapping().getType(column.getClassName());
		if (javaType == null) {
			// TODO java.lang.Object
			dto.setJavaType("java.lang.Object");
		} else {
			dto.setJavaType(javaType);
		}
		String fieldName = "";
		if (column.getName() != null) {
			String []item = column.getName().split("_");
			for (int i = 0; i < item.length; i++) {
				if (i != 0) {
					fieldName += StringUtils.firstCharUpper(item[i]);
				} else {
					fieldName = item[i];
				}
			}
		}
		dto.setFieldName(fieldName);
		return dto;
	}

	public static List<ColumnDTO> toDTO(List<Column> listColumn) {
		if (listColumn == null) {
			return null;
		}
		List<ColumnDTO> list = new ArrayList<ColumnDTO>();
		for (Column column : listColumn) {
			list.add(toDTO(column));
		}
		return list;
	}
}
