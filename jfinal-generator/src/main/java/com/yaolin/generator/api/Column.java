package com.yaolin.generator.api;
/**
 * 字段信息
 * <ol>
 * <li>name [String] 字段名</li>
 * <li>sqlType [int] sql类型 sql.Type</li>
 * <li>typeName [String] VARCHAR..</li>
 * <li>className [String] java.lang.Long...</li>
 * <li>displayLenth [int] varchar(255):255，decimal(10,2):12</li>
 * <li>size [Integer] maybe null</li>
 * <li>decimalDigits [Integer] maybe null</li>
 * <li>primaryKey [boolean] 是否为主键</li>
 * <li>autoIncrement [boolean] 是否自增</li>
 * <li>nullable [boolean]  可否为NULL</li>
 * <li>defaultValue [Object] 默认值</li>
 * <li>remark [String] 字段备注</li>
 * <li>table [Table] 所属表</li>
 * </ol>
 * @author yaoin
 * @see Table
 */
public class Column {

	private String name; // 字段名
	private int sqlType; // sql类型 sql.Type
	private String typeName; // VARCHAR..
	private String className; // java.lang.Long
	private int displayLenth; // varchar(255):255，decimal(10,2):12
	private Integer size; // maybe null
	private Integer decimalDigits;// maybe null
	private boolean primaryKey; // 是否为主键
	private boolean autoIncrement; // 是否自增
	private boolean nullable; // 可否为NULL
	private Object defaultValue;
	private String remark; // 字段备注
	
	private Table table; // 所属表

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSqlType() {
		return sqlType;
	}
	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getDisplayLenth() {
		return displayLenth;
	}
	public void setDisplayLenth(int displayLenth) {
		this.displayLenth = displayLenth;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
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
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
}
