package com.xhy.Utils;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivitiUtil {

//    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-activiti.xml");
//        RepositoryService repositoryService = (RepositoryService) ac.getBean("repositoryService");
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("processes/audit.bpmn20.xml")
//                .addClasspathResource("processes/audit.bpmn20.png")
//                .name("需求申请流程")
//                .key("need")
//                .deploy();
//        System.out.println(deployment.getId());
//        System.out.println(deployment.getName());
//    }

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-activiti.xml");
        RepositoryService repositoryService = (RepositoryService) ac.getBean("repositoryService");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/auditBuy.bpmn20.xml")
                .addClasspathResource("processes/auditBuy.bpmn20.png")
                .name("采购申请流程")
                .key("buy")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }
}
