package ${basePackageName}.${table.module}.${table.modelName?lower_case};

import ${basePackageName}.${table.module}.${table.modelName?lower_case}.base.Base${table.modelName};
import com.jfinal.plugin.activerecord.Page;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * ${table.modelName} model.
 * 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class ${table.modelName} extends Base${table.modelName}<${table.modelName}> {
	
	public static final ${table.modelName} me = new ${table.modelName}();
	
	/**
	 * 所有 sql 与业务逻辑写在 Service 中，在此放在 Model 中仅为示例，
	 * 不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<${table.modelName}> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from ${table.name}");
	}
}
