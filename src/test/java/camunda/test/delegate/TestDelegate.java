package camunda.test.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestDelegate implements JavaDelegate {

  //Camunda model info is hidden under elementType property, type under typeName and its attributes listed in the attributes of the object
  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    String processId = delegateExecution.getProcessDefinitionId();
    String currentActivityName = delegateExecution.getCurrentActivityName();
    String currentActivityId = delegateExecution.getCurrentActivityId();
    log.info("Executing activity {}", currentActivityName);

    //Extension properties
    FlowElement flowElement = delegateExecution.getBpmnModelElementInstance();
    ExtensionElements extensionElements = flowElement.getExtensionElements();
    for(ModelElementInstance modelElementInstance: extensionElements.getElements()){
      if (modelElementInstance instanceof CamundaProperties) {
        CamundaProperties properties = (CamundaProperties) modelElementInstance;
        for(CamundaProperty property: properties.getCamundaProperties()) {
          log.info("Extension property Id: " + property.getCamundaId() + " Name: " + property.getCamundaName() + " Value: " + property.getCamundaValue());
        }
      }
    }

    BpmnModelInstance modelInstance = delegateExecution.getBpmnModelInstance();
    Task task = modelInstance.getModelElementById(currentActivityId);
    // find sequence flow by id
    Collection<SequenceFlow> incoming = task.getIncoming();

    // get all outgoing sequence flows of the source
    List<FlowNode> sources = incoming.stream().map(flow -> flow.getSource()).collect(Collectors.toList());
    if (sources.size() == 1){
      FlowNode node = sources.get(0);
      if (node instanceof StartEvent){
        //super activity of subprocess
        DelegateExecution superExecution = delegateExecution.getSuperExecution();
        if (superExecution != null) {
          String superActivity = superExecution.getCurrentActivityName();
          Assert.assertNotNull(superActivity);
        }
      } else if (node instanceof Gateway){
        //sources are beyond gateway
        Collection<SequenceFlow> gatewayIncoming = node.getIncoming();
        sources = gatewayIncoming.stream().map(flow -> flow.getSource()).collect(Collectors.toList());
      }
    }
    Assert.assertTrue(sources.size()>0 && (
      (currentActivityName.compareToIgnoreCase("Task31b")==0 && sources.size() == 2) ||
        (currentActivityName.compareToIgnoreCase("Task31")==0 && sources.size() == 2) ||
        sources.size() == 1
      )
    );
  }
}
