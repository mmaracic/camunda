package camunda.test.model;

import camunda.test.WebappExampleProcessApplication;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DirtiesContext
@SpringBootTest
@ContextConfiguration(classes={WebappExampleProcessApplication.class})
public class CleanTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private RepositoryService repositoryService;

  @Test()
  public void cleanTest(){
    cleanCamunda();
  }

  private void cleanCamunda(){
    log.info("Cleaning camunda");
    //deployed process definitions
    List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
      .orderByProcessDefinitionVersion()
      .asc()
      .list();
    List<String> defNames = processDefinitions.stream().map(def -> def.getName()).collect(Collectors.toList());
    log.info("Process names: {}", defNames);
    List<String> defKeys = processDefinitions.stream().map(def -> def.getKey()).collect(Collectors.toList());
    log.info("Process keys: {}", defKeys);
    processDefinitions.stream().forEach(def -> repositoryService.deleteDeployment(def.getDeploymentId(), true));

    //runtime instances
    List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
      .orderByProcessDefinitionId()
      .asc()
      .list();
    List<String> instBusKeys = instances.stream().map(inst -> inst.getBusinessKey()).collect(Collectors.toList());
    log.info("Process instance business keys: {}", instBusKeys);
    List<String> instDefIds = instances.stream().map(def -> def.getProcessDefinitionId()).collect(Collectors.toList());
    log.info("Process instance definition ids: {}", instDefIds);
  }

}
