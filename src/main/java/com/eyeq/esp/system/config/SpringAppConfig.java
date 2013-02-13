package com.eyeq.esp.system.config;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Hana Lee
 * @since 0.1.1 2013. 2. 2. 오후 1:06:46
 * @revision $LastChangedRevision: 6000 $
 * @date $LastChangedDate: 2013-02-12 21:09:28 +0900 (화, 12 2월 2013) $
 * @by $LastChangedBy: samkwang.na $
 */
@Configuration
@ComponentScan(basePackages = "com.eyeq.esp", excludeFilters = {
		@ComponentScan.Filter(Configuration.class),
		@ComponentScan.Filter(Controller.class) })
@EnableTransactionManagement
@ImportResource(value = { "classpath*:esp-test-security-context.xml" })
public class SpringAppConfig {

	@Bean(name = "dataSource_oracle", destroyMethod = "close")
	public DataSource dataSource_oracle() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@www.dropletsap.com:1521:orcl");
		dataSource.setUsername("esp");
		dataSource.setPassword("esp");
		dataSource.setValidationQuery("SELECT 1 FROM DUAL");
		return dataSource;
	}

	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/test");
		dataSource.setUsername("root");
		dataSource.setPassword("eyeq");
		return dataSource;
	}

	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean connectionFactoryBean = new LocalContainerEntityManagerFactoryBean();
		connectionFactoryBean.setPersistenceUnitName("persistenceUnit");
		connectionFactoryBean.setDataSource(dataSource());
		connectionFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		return connectionFactoryBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		/*
		 * jpaVendorAdapter
		 * .setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
		 */
		/* jpaVendorAdapter.setDatabase(Database.ORACLE); */
		jpaVendorAdapter
				.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		return jpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager jpaTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		return jpaTransactionManager;
	}

	@Bean
	public MultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5000000);
		multipartResolver.setUploadTempDir(fileSystemResource());
		return multipartResolver;
	}

	@Bean
	public Resource fileSystemResource() {
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("esp.home"));
		sb.append("temp");
		sb.append(System.getProperty("file.separator"));

		FileSystemResource resource = new FileSystemResource(sb.toString());
		return resource;
	}
}
