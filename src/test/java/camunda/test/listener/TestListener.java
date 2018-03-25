package camunda.test.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.junit.experimental.categories.Category;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution execution) throws Exception {
    String processId = execution.getProcessDefinitionId();
    String currentActivityName = execution.getCurrentActivityName();
    String currentActivityId = execution.getCurrentActivityId();
    log.info("Listener in activity {}", currentActivityName);

  }
}
