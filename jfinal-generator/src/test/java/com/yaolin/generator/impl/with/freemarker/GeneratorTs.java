package com.yaolin.generator.impl.with.freemarker;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.yaolin.generator.api.Column;
import com.yaolin.generator.api.Table;
import com.yaolin.generator.impl.with.freemarker.generator.FreemarkerGenerator;
/**
 * 
 * @author yaolin
 *
 */
public class GeneratorTs {

	@Test
	public void test() {
		Table table = new Table();
		table.setName("demo_blog");
		table.setRemark("博客");
		
		List<Column> listColumn = new ArrayList<Column>();
		Column column = new Column();
		column.setClassName("java.lang.String");
		column.setName("title");
		column.setNullable(false);
		column.setRemark("标题");
		column.setTable(table);
		listColumn.add(column);
		//
		column = new Column();
		column.setClassName("java.lang.String");
		column.setName("content");
		column.setNullable(false);
		column.setRemark("内容");
		column.setTable(table);
		listColumn.add(column);
		
		table.setListColumn(listColumn);
		
		String tmpl = "D:/ideaprojectes/jfinal-wscs/jfinal-generator/src/main/resources";
		new FreemarkerGenerator().generate(tmpl, 
			"com.yaolin.jfinal.generator",
			tmpl + "/code/src/main/java/",
			tmpl + "/code/src/main/resources/",
			tmpl + "/code/src/main/webapp/js/", 
			tmpl + "/code/src/main/webapp/html/",
			table);
	}
}
