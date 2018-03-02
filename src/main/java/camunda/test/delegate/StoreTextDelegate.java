/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.delegate;

import camunda.test.jpa.model.Text;
import camunda.test.service.TextService;
import static camunda.test.util.Constants.TEXT;
import static camunda.test.util.Constants.TEXT_ID;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marijo
 */
@Slf4j
@Component
public class StoreTextDelegate implements JavaDelegate{

    @Autowired
    private TextService service;
    
    @Override
    public void execute(DelegateExecution de) throws Exception {
        log.info("Entering delegate "+this.getClass().getSimpleName());
        String submittedText = (String) de.getVariable(TEXT);
        de.removeVariable(TEXT);
        Text text = service.storeText(submittedText);
        de.setVariable(TEXT_ID, text.getId());
        log.info("Exiting delegate "+this.getClass().getSimpleName());
    }
    
}
