package com.example.demo.controller;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CreateActiviti {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Test
    public void Test(){
        System.out.println(repositoryService);
    }
    private ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("197620");

        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        return processEngine;
    }

    @Test
    @RequestMapping("index1")
    public void deployProcess() {
        ProcessEngine processEngine = getProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("test1.bpmn20.xml")
                .deploy();
    }

    @Test
    public void startProcess() {
        ProcessEngine processEngine = getProcessEngine();
        runtimeService = processEngine.getRuntimeService();
        String processDefinitionKey = "myProcess";
        String business_key = "1111";
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee1","admin1");
        ProcessInstance processInstance
                = runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);
        System.out.println(processInstance.getId());
    }
}
