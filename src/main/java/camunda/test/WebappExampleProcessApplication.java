package camunda.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import camunda.test.util.CustomCamundaProcessEngineConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaProcessEngineConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 *
 * @author Marijo
 */
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"camunda.test.controller", "camunda.test.delegate", "camunda.test.service"})
@EntityScan(basePackages = {"camunda.test.jpa.model"})
@EnableJpaRepositories(basePackages = {"camunda.test.jpa.repository"})
public class WebappExampleProcessApplication {

    public static void main(String... args) {
        SpringApplication.run(WebappExampleProcessApplication.class, args);
    }

    @Bean
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return (HikariDataSource) properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(HikariDataSource hikariDataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(hikariDataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public static CamundaProcessEngineConfiguration camundaProcessEngineConfiguration(HikariDataSource hikariDataSource, DataSourceTransactionManager dataSourceTransactionManager) {
        return new CustomCamundaProcessEngineConfiguration(hikariDataSource,dataSourceTransactionManager);
    }

/*
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration(HikariDataSource hikariDataSource, DataSourceTransactionManager dataSourceTransactionManager){
        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
        springProcessEngineConfiguration.setDataSource(hikariDataSource);
        springProcessEngineConfiguration.setTransactionManager(dataSourceTransactionManager);
        springProcessEngineConfiguration.setDatabaseSchemaUpdate("create");
        return springProcessEngineConfiguration;
    }
*/
}
