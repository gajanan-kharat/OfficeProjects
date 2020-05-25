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
@EnableJpaRepositories(entityManagerFactoryRef="quartzEntityManagerFactory", transactionManagerRef="quartzTransactionManager",
basePackages= {"com.citi.cmb.gce.accountservices.quartz.*"})
public class QuartzDbConfig {
	
	@Autowired
	private Logger logger;
	
	@Autowired
	@Qualifier("quartzdatasource")
	DataSource dataSource;
	
	@Bean(name="quartzEntityManager")
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Bean(name = "quartzEntityManagerFactory")
	@Primary
	private EntityManagerFactory entityManagerFactory() {
		try {
			LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
			emf.setDataSource(dataSource);
			HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
			emf.setJpaVendorAdapter(jpaVendorAdapter);
			emf.setPackagesToScan("com.citi.cmb.gce.accountservices.quartz.entity");
			emf.setPersistenceUnitName("quartz");
			emf.afterPropertiesSet();
			return emf.getObject();
		} catch (Exception e) {
			logger.error("Exception occurred");
		}
		return null;
	}
	
	@Bean(name="quartzSessionFactory")
	public SessionFactory getSessionFactory() {
		if(entityManagerFactory().unwrap(SessionFactory.class)==null) {
			throw new NullPointerException("Factory is not hibernate factory");
		}
		return entityManagerFactory().unwrap(SessionFactory.class);
	}
	
	@Bean(name="quartzTransactionManager")
	public PlatformTransactionManager transsactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactory());
		return manager;
	}
}
