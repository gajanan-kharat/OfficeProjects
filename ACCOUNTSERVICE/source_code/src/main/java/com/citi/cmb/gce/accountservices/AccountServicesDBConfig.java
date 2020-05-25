package com.citi.cmb.gce.accountservices;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(entityManagerFactoryRef="accountServicesEntityManagerFactory",
transactionManagerRef="accountServicesTransactionManager",
basePackages= {"com.citi.cmb.gce.accountservices.common.repository",
"com.citi.cmb.gce.accountservices.msg.repository",
"com.citi.cmb.gce.accountservices.postrestriction.audit.repository"})

public class AccountServicesDBConfig {
	
	@Autowired
	private Logger logger;
	
	@Autowired
	@Qualifier("accountServicesDataSource")
	DataSource dataSource;
	
	@Bean(name="accountServicesEntityManager")
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Bean(name = "accountServicesEntityManagerFactory")
	@Primary
	private EntityManagerFactory entityManagerFactory() {
		try {
			LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
			emf.setDataSource(dataSource);
			HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
			emf.setJpaVendorAdapter(jpaVendorAdapter);
			emf.setPackagesToScan("com.citi.cmb.gce.accountservices.common.entity",
					"com.citi.cmb.gce.accountservices.msg.entity",
					"com.citi.cmb.gce.accountservices.postrestriction.audit.entity");
			emf.setPersistenceUnitName("accountservices");
			emf.afterPropertiesSet();
			return emf.getObject();
		} catch (Exception e) {
			logger.error("Exception occurred");
		}
		return null;
	}
	
	@Bean(name="accountServicesSessionFactory")
	public SessionFactory getSessionFactory() {
		if(entityManagerFactory().unwrap(SessionFactory.class)==null) {
			throw new NullPointerException("Factory is not hibernate factory");
		}
		return entityManagerFactory().unwrap(SessionFactory.class);
	}
	
	@Bean(name="accountServicesTransactionManager")
	public PlatformTransactionManager transsactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactory());
		return manager;
	}

}
