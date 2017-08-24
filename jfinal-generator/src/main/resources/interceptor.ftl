package ${basePackageName}.${table.module}.${table.modelName?lower_case};

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * ${table.modelName}Interceptor
 * 此拦截器仅做为示例展示，在本 demo 中并不需要
 */
public class ${table.modelName}Interceptor implements Interceptor {
	
	public void intercept(Invocation inv) {
		System.out.println("Before invoking " + inv.getActionKey());
		inv.invoke();
		System.out.println("After invoking " + inv.getActionKey());
	}
}
