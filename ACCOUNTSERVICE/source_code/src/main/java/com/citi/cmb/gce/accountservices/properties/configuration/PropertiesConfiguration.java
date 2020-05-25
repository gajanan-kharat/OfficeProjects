package com.citi.cmb.gce.accountservices.properties.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.citi.cmb.gce.accountservices.constant.ASConstant;
import com.citi.cmb.gce.accountservices.logger.AccountServiceLogger;

@Configuration
public class PropertiesConfiguration {

	@Bean
	public static PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		Resource [] resources= new ClassPathResource[] { new ClassPathResource(ASConstant.CONFIG_PROPERTIES)};
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
	
	@Bean
	@Scope(value="prototype")
	public Logger logger(InjectionPoint injectionPoint) {
		Class<?> targetClass = injectionPoint.getMember().getDeclaringClass();
		return new AccountServiceLogger(LoggerFactory.getLogger(targetClass), targetClass.getName());
	}
}
