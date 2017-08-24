package com.xxx.market.web.core.utils.tomcat;

public class TomcatBootStrap {
	public static void main(String[] args) {
		new TomcatBootstrapHelper(8094, true, "dev").start();
	}
}
