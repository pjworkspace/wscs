package ${basePackageName}.${table.module}.${table.modelName?lower_case};

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import ${basePackageName}.${table.module}.${table.modelName?lower_case}.Blog;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 *${table.modelName}Controller
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(${table.modelName}Interceptor.class)
public class ${table.modelName}Controller extends Controller {
	public void index() {
		setAttr("${table.modelName?uncap_first}Page", ${table.modelName}.me.paginate(getParaToInt(0, 1), 10));
		render("table.html");
	}
	
	public void add() {
	}
	
	@Before(${table.modelName}Validator.class)
	public void save() {
		getModel(${table.modelName}.class).save();
		redirect("/${table.modelName?uncap_first}");
	}
	
	public void edit() {
		setAttr("${table.modelName?uncap_first}", ${table.modelName}.me.findById(getParaToInt()));
	}
	
	@Before(${table.modelName}Validator.class)
	public void update() {
		getModel(${table.modelName}.class).update();
		redirect("/${table.modelName?uncap_first}");
	}
	
	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/${table.modelName?uncap_first}");
	}
}


