package com.brubank.hotels.deploy;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Spring Boot configuration the repositories to use SqlLite with Hibernate
 */
@Configuration
@EnableJpaRepositories("com.brubank.hotels")
public class SqlLiteHibernateRepositoriesConfiguration {

  /** The repositories package */
  private static final String REPOSITORIES_PACKAGE = "com.brubank.hotels";
  /** The repositories entities package */
  private static final String REPOSITORIES_ENTITIES = "com.brubank.hotels.reservations";


  @Autowired
  private Environment environment;

  @Bean
  public DataSource dataSource(
      @Value("${spring.datasource.driver-class-name}") final String driverProperty,
      @Value("${spring.datasource.url}") final String databaseUrl)  {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverProperty);
    dataSource.setUrl(databaseUrl);
    return dataSource;
  }

  /**
   * Creates the EntityManagerFactory used by the Spring ORM implementation
   *
   * @param dataSource the {@link DataSource} used by the EntitiManagerFactory
   * @return {@link LocalContainerEntityManagerFactoryBean} instance
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      final DataSource dataSource,
      @Value("${spring.jpa.hibernate.ddl-auto}") final String hibernateStrategy,
      @Value("${spring.jpa.hibernate.show_sql}") final String hibernateShowSql,
      @Value("${spring.jpa.hibernate.formal_sql}") final String hibernateFormalSql,
      @Value("${spring.jpa.properties.hibernate.dialect}") final String hibernateDialect) {
    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(dataSource);
    entityManagerFactory
        .setPackagesToScan(REPOSITORIES_PACKAGE, REPOSITORIES_ENTITIES);

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
    entityManagerFactory.setJpaProperties(getJpaProperties(
        hibernateStrategy,
        hibernateShowSql,
        hibernateFormalSql,
        hibernateDialect));

    return entityManagerFactory;
  }

  private Properties getJpaProperties(
      final String hibernateStrategy,
      final String hibernateShowSql,
      final String hibernateFormalSql,
      final String hibernateDialect) {
    Properties jpaProperties = new Properties();
    jpaProperties
        .put("hibernate.hbm2ddl.auto", hibernateStrategy);
    jpaProperties.put("hibernate.show_sql", hibernateShowSql);
    jpaProperties
        .put("hibernate.formal_sql", hibernateFormalSql);
    jpaProperties.put("hibernate.dialect", hibernateDialect);
    return jpaProperties;
  }
}
