package com.yaolin.generator.api;

/**
 * 根据数据库表生成相关代码的接口
 * @author yaoin
 */
public interface Generator {

	/**
	 * 根据数据库表生成相关代码
	 * @param templateFileDir 模板文件目录（带盘符）
	 * @param basePackageName 项目包名
	 * @param javaFileDir java文件目录
	 * @param resourceFileDir 资源文件目录
	 * @param jsFileDir js文件目录
	 * @param viewFileDir 视图文件目录
	 * @param table 数据库表信息
	 * @see Table
	 */
	public void generate(String templateFileDir, String basePackageName,
		String javaFileDir, String resourceFileDir, String jsFileDir, String viewFileDir, Table table);
}
