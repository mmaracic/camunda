package camunda.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Marijo
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = {"camunda.test.controller", "camunda.test.delegate", "camunda.test.listener", "camunda.test.service"})
@EntityScan(basePackages = {"camunda.test.jpa.model"})
@EnableJpaRepositories(basePackages = {"camunda.test.jpa.repository"})
public class WebappExampleProcessApplication {

    public static void main(String... args) {
        SpringApplication.run(WebappExampleProcessApplication.class, args);
    }
}
