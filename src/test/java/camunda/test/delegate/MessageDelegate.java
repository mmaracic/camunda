package camunda.test.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageDelegate implements JavaDelegate {

  @Autowired
  private RuntimeService runtimeService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    String processId = delegateExecution.getProcessDefinitionId();
    String currentActivityName = delegateExecution.getCurrentActivityName();
    String currentActivityId = delegateExecution.getCurrentActivityId();
    log.info("Activity {}",currentActivityName);

    ProcessEngineServices engineServices = delegateExecution.getProcessEngineServices();
    String processDefintionKey = engineServices.getRepositoryService().getProcessDefinition(processId).getKey();

    String messageName = null;
    if (currentActivityName.endsWith("21c")) messageName = "Message1";
    else if (currentActivityName.endsWith("12c")) messageName = "Message2";
    else{
      log.info("Activity {} in message delegate", currentActivityName);
      return;
    }
    try {
      log.info("Sending message {}", messageName);
      runtimeService.correlateMessage(messageName);
      log.info("Sending message {} completed", messageName);
    }catch(MismatchingMessageCorrelationException ex){
      log.error(ex.getMessage());
    }
  }
}
