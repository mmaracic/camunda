package camunda.test.util;

import com.zaxxer.hikari.HikariDataSource;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.DefaultProcessEngineConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class CustomCamundaProcessEngineConfiguration extends DefaultProcessEngineConfiguration {

    private HikariDataSource hikariDataSource;
    private DataSourceTransactionManager dataSourceTransactionManager;

    public CustomCamundaProcessEngineConfiguration(HikariDataSource hikariDataSource, DataSourceTransactionManager dataSourceTransactionManager){
        this.hikariDataSource = hikariDataSource;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Override
    public void preInit(SpringProcessEngineConfiguration configuration) {
        super.preInit(configuration);
        configuration.setJobExecutorAcquireByPriority(true);
        configuration.setProducePrioritizedJobs(true);
        configuration.setDataSource(hikariDataSource);
        configuration.setTransactionManager(dataSourceTransactionManager);
    }
}
