package camunda.test.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogDelegate implements JavaDelegate{

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    String processId = delegateExecution.getProcessDefinitionId();
    String currentActivityName = delegateExecution.getCurrentActivityName();
    String currentActivityId = delegateExecution.getCurrentActivityId();
    log.info("Executing activity {}", currentActivityName);
  }
}
