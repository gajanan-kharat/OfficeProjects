package com.citi.cmb.gce.accountservices;

import java.net.URL;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import com.citi.cmb.gce.accountservices.constant.ASConstant;
import com.citi.cmb.gce.accountservices.msg.listener.MessageReceiver;

@Configuration
@Import(value = AccountServicesApplication.class)
public class GceJmsConfig {

	@Autowired
	private MessageReceiver msgReceiver;
	
	@Bean(name="gceJndiTemplate")
	public JndiTemplate geJndiTemplate(Environment environment) {
		Properties properties = new Properties();
		ClassLoader classLoader = new AccountServicesApplication().getClass().getClassLoader();
		URL url = classLoader.getResource("gce_158224.p12");
		
		properties.setProperty("java.naming.provider.url", environment.getProperty(ASConstant.JMS_PROVIDER_URL));
		properties.setProperty("java.naming.factory.initial", environment.getProperty(ASConstant.JMS_INITITAL_CF));
		properties.setProperty("java.naming.security.principal", environment.getProperty(ASConstant.JMS_PRINCIPLE));
		properties.setProperty("java.naming.security.credentials", environment.getProperty(ASConstant.JMS_CRED));
		properties.setProperty("java.naming.security_protocol", environment.getProperty(ASConstant.JMS_SECURITY_PROTOCOL));
		properties.setProperty("com.tibco.tibcojms.naming.ssl_identity", url.getPath());
		properties.setProperty("com.tibco.tibcojms.naming.ssl_password",  environment.getProperty(ASConstant.JMS_SSL_PASSWORD));
		properties.setProperty("com.tibco.tibcojms.naming.enable_verify_host",  environment.getProperty(ASConstant.JMS_ENABLE_VERIFY_HOST));
		properties.setProperty("com.tibco.tibcojms.naming.ssl_auth_only",  environment.getProperty(ASConstant.JMS_SSL_AUTH_ONLY));
		properties.setProperty("com.tibco.tibcojms.naming.ssl_vendor",  environment.getProperty(ASConstant.JMS_SSL_VENDOR));
		
		return new JndiTemplate(properties);
	}
	
	@Primary
	@Bean(name="gceConnectionFactory")
	public JndiObjectFactoryBean getGceConnectionFactory(JndiTemplate jndiTemplate, Environment environment) {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiTemplate(jndiTemplate);
		bean.setJndiName(environment.getProperty(ASConstant.JMS_CONNECTION_FACTORY));
		return bean;
	}
	
	@Bean(name = "userGceConnectionFactory")
	public UserCredentialsConnectionFactoryAdapter getUserConnectionFactory(
			@Qualifier("gceConnectionFactory") JndiObjectFactoryBean connectionFactory, Environment environment) {
		UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
		adapter.setUsername(environment.getProperty(ASConstant.JMS_PRINCIPLE));
		adapter.setPassword(environment.getProperty(ASConstant.JMS_CRED));
		ConnectionFactory factory = (ConnectionFactory) connectionFactory.getObject();
		adapter.setTargetConnectionFactory(factory);
		return adapter;
	}
	
	@Bean(name="gceJmsTemplate")
	public JmsTemplate getGceJmsTemplate(
			@Qualifier("userGceConnectionFactory") UserCredentialsConnectionFactoryAdapter factory) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory((ConnectionFactory)factory);
		return jmsTemplate;
	}
	
	@Bean(name="messageResponseQueue")
	public JndiObjectFactoryBean getMessageResponseQueue(@Qualifier("gceJmsTemplate") JndiTemplate jndiTemplate, Environment environment) {
		JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
		factoryBean.setJndiTemplate(jndiTemplate);
		factoryBean.setJndiName(environment.getProperty(ASConstant.JMS_QUEUE_URL));
		return factoryBean;
	}
	
	@Bean(name="messageListener")
	public DefaultMessageListenerContainer getMessageListner(@Qualifier("userGceConnectionFactory") UserCredentialsConnectionFactoryAdapter factory,
			@Qualifier("messageResponseQueue") JndiObjectFactoryBean queue) {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		Destination destination = (Destination)queue.getObject();
		container.setDestination(destination);
		container.setConnectionFactory((ConnectionFactory)factory);
		container.setMessageListener(this.msgReceiver);
		
		return container;
	}
	
	
	
	
	
	
	
}
