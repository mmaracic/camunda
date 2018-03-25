package camunda.test.model;

import camunda.test.WebappExampleProcessApplication;
import camunda.test.delegate.TestDelegate;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Slf4j
@DirtiesContext
@SpringBootTest
@ContextConfiguration(classes={WebappExampleProcessApplication.class})
public class BpmnModelTest extends AbstractTestNGSpringContextTests {

  private final String TEST_SCENARIO1 = "Test1";
  private final String TEST_SCENARIO2 = "Test2";
  private final String TEST_SCENARIO3 = "Test3";

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TestDelegate testDelegate;

  @Test(enabled = false)
  public void testScenario1(){
    ProcessInstance process = runtimeService.createProcessInstanceByKey(TEST_SCENARIO1).execute();
  }

  @Test(enabled = false)
  public void testScenario2(){
    ProcessInstance process = runtimeService.createProcessInstanceByKey(TEST_SCENARIO2).execute();
  }

  @Test()
  public void testScenario3(){
    try {
      ProcessInstance process = runtimeService.createProcessInstanceByKey(TEST_SCENARIO3).execute();
    } catch(Exception ex){
      log.error(ex.getMessage());
    }
    try {
      Thread.sleep(50000);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    }
  }
}
