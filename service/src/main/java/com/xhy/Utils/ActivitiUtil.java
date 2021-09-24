package com.xhy.Utils;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivitiUtil {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-activiti.xml");
        RepositoryService repositoryService = (RepositoryService) ac.getBean("repositoryService");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/audit.bpmn20.xml")
                .addClasspathResource("processes/audit.bpmn20.png")
                .name("需求申请流程")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }
}
