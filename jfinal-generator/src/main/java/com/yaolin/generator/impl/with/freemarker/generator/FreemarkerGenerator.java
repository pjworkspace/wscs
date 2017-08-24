package com.yaolin.generator.impl.with.freemarker.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yaolin.generator.api.Generator;
import com.yaolin.generator.api.Table;
import com.yaolin.generator.impl.with.freemarker.dto.ColumnDTO;
import com.yaolin.generator.impl.with.freemarker.dto.TableDTO;
import com.yaolin.generator.impl.with.freemarker.utils.FileUtils;

import freemarker.template.Configuration;
/**
 * Demo For Custom
 * @author yaoin
 */
public class FreemarkerGenerator implements Generator {

	@Override
	public void generate(String templateFileDir, String basePackageName, String javaFileDir, String resourceFileDir,
		String jsFileDir, String viewFileDir, Table table) {
		
		Map<String, Object> root = new HashMap<String,Object>();
		root.put("basePackageName", basePackageName);
		TableDTO tableDTO = TableDTO.toDTO(table);
		root.put("table", tableDTO);
		root.put("listColumn", ColumnDTO.toDTO(table.getListColumn()));
		
		String javaPath = FileUtils.combine(javaFileDir,basePackageName.replace(".", File.separator) + File.separator + tableDTO.getModule() + File.separator + tableDTO.getModelName().toLowerCase());
		String viewPath = FileUtils.combine(viewFileDir, tableDTO.getModule() + File.separator + tableDTO.getModelName().toLowerCase());
		//
		String[][] target = {
			{ "baseModel.ftl", FileUtils.combine(javaPath, "/base/"), "Base" + tableDTO.getModelName() + ".java" }, //baseModel
			{ "model.ftl", javaPath, tableDTO.getModelName() + ".java"}, // model
			{ "controller.ftl", javaPath, tableDTO.getModelName() + "Controller.java"}, // controller
			{ "interceptor.ftl", javaPath, tableDTO.getModelName() + "Interceptor.java"}, // interceptor
			{ "validator.ftl", javaPath, tableDTO.getModelName() + "Validator.java"}, // validator
			{ "_form.ftl", viewPath, "_form.html"}, // _form.html
			{ "add.ftl", viewPath, "add.html"}, // _form.html
			{ "edit.ftl", viewPath, "edit.html"}, // _form.html
			{ "table.ftl", viewPath, "table.html"} // _form.html
		};
		File f = null;
		for (int i = 0; i < target.length; i++) {
			f = new File(FileUtils.combine(target[i][1], target[i][2]));
			if ( !(f.exists() && f.isFile()) ) { 
				renderTemplateFile(root, templateFileDir, target[i][0], target[i][1], target[i][2]);
			}
		}
	}
	
	private Configuration cfg = null;
	private void renderTemplateFile(Map<String, Object> root,String templateDir, String templateName, String fileDir, String fileName) {
		FileWriter fw = null;
		try {
			if (cfg == null) {
				cfg = new Configuration(Configuration.VERSION_2_3_0);
			}
			cfg.setDirectoryForTemplateLoading(new File(templateDir));
			File dir = new File(fileDir);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			fw = new FileWriter(FileUtils.combine(fileDir, fileName));
			cfg.getTemplate(templateName).process(root, fw);
			fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fw != null) {
					fw.close();
					fw = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
