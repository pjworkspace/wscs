package com.yaolin.generator.api;

import java.util.List;

/**
 * 数据库表信息
 * <ol>
 * <li>name [String] 表名</li>
 * <li>remark [String] 备注</li>
 * <li>listColumn [List&lt;Column&gt;] 表字段集合</li>
 * </ol>
 * @author yaoin
 * @see Column
 */
public class Table {

	private String name; // 表名
	private String remark;// 备注
	private List<Column> listColumn;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Column> getListColumn() {
		return listColumn;
	}
	public void setListColumn(List<Column> listColumn) {
		this.listColumn = listColumn;
	}
}
