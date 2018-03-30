package com.spring.springPropertiesEditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringPropertiesEditorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringPropertiesEditorApplication.class, args);
//		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.DEBUG);
//		applicationContext.getBean(org.springframework.cloud.context.scope.refresh.RefreshScope.class).refreshAll();
	}
}
