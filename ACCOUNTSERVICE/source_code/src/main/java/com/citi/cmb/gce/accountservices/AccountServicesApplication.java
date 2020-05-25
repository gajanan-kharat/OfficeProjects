package com.citi.cmb.gce.accountservices;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.citi.cmb.gce.accountservices.constant.ASConstant;
import com.citi.cmb.gce.accountservices.msg.mapper.AcctMainInfoEntityIdMapper;

@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
@SpringBootApplication
@EnableScheduling
public class AccountServicesApplication {

	@Autowired
	Environment environment;
	
	@Autowired
	AcctMainInfoEntityIdMapper acctMainInfoEntityIdMapper;
	
	public static void main(String[] args) {
		SpringApplication.run(AccountServicesApplication.class, args);
	}
	
	@Primary
	@Bean(name="accountServicesDataSource")
	public DataSource getAccountServicesDatasource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("oracle.jdbc.driver.OracleDriver");
		dataSourceBuilder.url(environment.getProperty(ASConstant.ACCOUNTSERVICES_ORCL_DB_URL));
		dataSourceBuilder.username(environment.getProperty(ASConstant.ACCOUNTSERVICES_ORCL_DB_USER));
		dataSourceBuilder.password(environment.getProperty(ASConstant.ACCOUNTSERVICES_ORCL_DB_CRED));
		return dataSourceBuilder.build();
	}
	
	@Bean(name="accountserviceJdbcTemplate")
	@Autowired
	public JdbcTemplate accountserviceJdbcTemplate(@Qualifier("accountServicesDataSource") DataSource accountServicesDataSource) {
		return new JdbcTemplate(accountServicesDataSource);
		
	}
	
	@Bean(name="quartzDatasource")
	@ConfigurationProperties(prefix="spring.quartzdatasource")
	public DataSource quartzDatasource() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.url("DB URL");
		builder.username("DB user");
		builder.password("DB password");
		return builder.build();
	}
	
	@Bean(name="quartzJdbcTemplate")
	@Autowired
	public JdbcTemplate quartzJdbcTemplate(@Qualifier("quartzDatasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
