/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.controller;

import static camunda.test.util.Constants.STATISTICS;
import static camunda.test.util.Constants.TEXT;
import static camunda.test.util.Constants.TEXT_CAMUNDA_PROCESS_NAME;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Marijo
 */
@Slf4j
@Controller
@RequestMapping(path = "/text", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TextController {
    
    @Autowired
    private RuntimeService runtimeService;
    
    @PostMapping(path = "/submit")
    @ResponseBody
    public Map<String,Long> submitText(@RequestBody String submittedText){
        Map<String, Object> variables = new HashMap<>();
        variables.put(TEXT, submittedText);
        ProcessInstanceWithVariables  process = runtimeService.createProcessInstanceByKey(TEXT_CAMUNDA_PROCESS_NAME).setVariables(variables).executeWithVariablesInReturn();
        VariableMap returnVariables = process.getVariables();
        Map<String,Long> statistics = returnVariables.getValue(STATISTICS, Map.class);
        return  statistics;
    }
    
}
