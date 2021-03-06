package com.xxx.market.web.core.config;

import java.util.HashMap;
import java.util.Map;

//import com.jfinal.template.Engine;
import org.apache.log4j.Logger;

import com.xxx.market.model._MappingKit;
import com.xxx.market.service.freemarker.method.AbbreviateMethod;
import com.xxx.market.service.freemarker.method.CurrencyMethod;
import com.xxx.market.service.freemarker.method.MessageMethod;
import com.xxx.market.web.core.handler.WeixinJssdkHandler;
import com.xxx.market.web.core.interceptor.MobileSessionInterceptor;
import com.xxx.market.web.core.plugin.spring.SpringDataSourcePlugin;
import com.xxx.market.web.core.plugin.spring.SpringPlugin;
import com.xxx.market.web.core.route.AutoBindRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.weixin.sdk.kit.WxSdkPropKit;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.StandardCompress;

public final class JFWebConfig extends JFinalConfig{

	private Logger logger = Logger.getLogger(JFWebConfig.class);
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 9999, "/mobile", 5);// 启动配置项
	}
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);//设置开发者模式
		loadPropertyFile("mobile.properties");
		PropKit.use("mobile.properties");
		me.setEncoding("UTF-8");
		me.setI18nDefaultBaseName("i18n");
		me.setMaxPostSize(1024 * 1024 * 10);
		me.setI18nDefaultLocale(getProperty("locale"));
		me.setError401View("/404.html");
		me.setError403View("/404.html");
		me.setError404View("/404.html");
		me.setError500View("/500.html");
	}

	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		me.add(new AutoBindRoutes());
	}

//	@Override
//	public void configEngine(Engine engine) {
//
//	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		/*DruidPlugin dataSourcePlugin = new DruidPlugin(getProperty("jdbc.url"), getProperty("jdbc.username"), getProperty("jdbc.password").trim());
		me.add(dataSourcePlugin);*/
		//spring plugin
		String [] configurations = new String [] {"classpath:applicationContext.xml"};
		SpringPlugin springPlugin = new SpringPlugin(configurations);
		me.add(springPlugin);
		
		//添加自动绑定model与表插件
		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(new SpringDataSourcePlugin());
		activeRecordPlugin.setShowSql(true);
		activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		_MappingKit.mapping(activeRecordPlugin);
		me.add(activeRecordPlugin);
		
		//缓存插件
		me.add(new EhCachePlugin());
		
	}

	/**
	 * 配置拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new MobileSessionInterceptor());
		//me.add(new ForbidInterceptor());
		me.add(new SessionInViewInterceptor());
	}

	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		//该处理器将request.getContextPath()存储在root中，可以解决路径问题
//	  	ContextPathHandler path = new ContextPathHandler("root");
		if(!PropKit.getBoolean("jfinal.devmode")) me.add(new WeixinJssdkHandler());
	}

	@Override
	public void afterJFinalStart() {
		Configuration cf = FreeMarkerRender.getConfiguration();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("img_domain", PropKit.get("img_domain"));
		map.put("locale", getProperty("locale"));
		map.put("webctx", JFinal.me().getContextPath());
    	map.put("compress", StandardCompress.INSTANCE);
    	map.put("message", new MessageMethod());
    	map.put("abbreviate", new AbbreviateMethod());
    	map.put("currency", new CurrencyMethod());
    	try {
			cf.setSharedVaribles(map);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
    	cf.setTemplateUpdateDelay(getPropertyToInt("template.update_delay", 0));
    	cf.setDefaultEncoding(getProperty("template.encoding"));
    	cf.setURLEscapingCharset(getProperty("url_escaping_charset"));
    	cf.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    	cf.setWhitespaceStripping(true);
    	cf.setClassicCompatible(true);
    	cf.setNumberFormat(getProperty("template.number_format"));
    	cf.setBooleanFormat(getProperty("template.boolean_format"));
    	cf.setDateTimeFormat(getProperty("template.datetime_format"));
    	cf.setDateFormat(getProperty("template.date_format"));
    	cf.setTimeFormat(getProperty("template.time_format"));
    	
//    	cf.setSharedVariable("shiro", new ShiroTags());
    	cf.setServletContextForTemplateLoading(JFinal.me().getServletContext(), getProperty("template.loader_path"));
    	
		
		loadPropertyFile("weixin.sdk.properties");
		WxSdkPropKit.use("weixin.sdk.properties");
		logger.info(" dbu mobile server is Ready !!");
		super.afterJFinalStart();
	}
	
	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
	}

}
