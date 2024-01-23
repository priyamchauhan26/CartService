package com.dropKart.CartService.config;

import java.util.Properties;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = "com.dropKart.commonDB.repo", entityManagerFactoryRef = "getepayDBEntityManager", transactionManagerRef = "getepayDBTransactionManager")
public class DropKartConfiguration {
	 @Autowired
	    Environment env;

	    public Properties setHibernateProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
	        properties.put("hibernate.hbm2ddl.auto", "update");
	        properties.put("hibernate.show_sql", "true");
	        return properties;
	    }

	    @Bean
	    public DataSource getpayDbDataSource() throws PropertyVetoException {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getProperty("dropkart.database.driver"));
	        dataSource.setUrl(env.getProperty("dropkart.database.url"));
	        dataSource.setUsername(env.getProperty("dropkart.database.username"));
	        dataSource.setPassword(env.getProperty("dropkart.database.password"));
	        return dataSource;
	    }

	    @Bean(name = "getepayDBEntityManager")
	    @Primary
	    public LocalContainerEntityManagerFactoryBean getepayDBEntityManager() throws PropertyVetoException {
	        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	        em.setDataSource(getpayDbDataSource());
	        em.setPackagesToScan(new String[] { "com.dropKart.commonDB.model" });
	        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	        em.setJpaVendorAdapter(vendorAdapter);
	        em.setJpaProperties(setHibernateProperties());
	        return em;
	    }

	    @Bean
	    @Primary
	    public PlatformTransactionManager getepayDBTransactionManager() throws PropertyVetoException {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(getepayDBEntityManager().getObject());
	        return transactionManager;
	    }
	 

}
