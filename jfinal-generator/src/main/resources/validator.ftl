package ${basePackageName}.${table.module}.${table.modelName?lower_case};

import package ${basePackageName}.${table.module}.${table.modelName?lower_case}.${table.modelName};
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * ${table.modelName}Validator.
 */
public class ${table.modelName}Validator extends Validator {
	
	protected void validate(Controller controller) {
		<#list listColumn as column>
		<#if column.nullable == false>
		validateRequiredString("${table.modelName?lower_case}.${column.fieldName}", "${column.fieldName}Msg", "请输入${table.remark}${column.remark}!");
		</#if>
		</#list>
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(${table.modelName}.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/${table.modelName?lower_case}/save"))
			controller.render("add.html");
		else if (actionKey.equals("/${table.modelName?lower_case}/update"))
			controller.render("edit.html");
	}
}
