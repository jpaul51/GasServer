package com.jonas.gas.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.jonas.gas.GasApplication;
import com.vividsolutions.jts.geom.GeometryFactory;



@Configuration
@EnableConfigurationProperties(GasProperties.class)
public class Config {

	

	
	
	@Bean("geometryFactory")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public GeometryFactory geometryFactory () {
		return new GeometryFactory();
	}

	
	@Bean("graphHopper")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public GraphHopper graphHopper()
	{

		return new GraphHopperOSM().forServer();
		
		
		
		
	}

	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUsername("postgres");
	    dataSource.setPassword("FY3W5r3T");
	 
	    dataSource.setUrl(
	      "jdbc:postgresql://localhost:5432/gas?createDatabaseIfNotExist=true"); 
	    
	    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	    
	     
	    return dataSource;
	}
	  @Bean
	  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	    em.setDataSource(dataSource);
	    em.setPackagesToScan(new String[] { "com.jonas.gas.model" });

	    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    em.setJpaVendorAdapter(vendorAdapter);

	    Properties properties = new Properties();
	    properties.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.postgis.PostgisDialect");
	    if(GasApplication.resetGasDb)
	    {
	    	  properties.setProperty("hibernate.hbm2ddl.auto", "create");
	    }
	    else
	    {
	    	  properties.setProperty("hibernate.hbm2ddl.auto", "none");
	    }
	  
	    em.setJpaProperties(properties);

	    return em;
	  }
}
