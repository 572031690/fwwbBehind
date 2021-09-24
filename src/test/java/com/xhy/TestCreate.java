package com.xhy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;

public class TestCreate {

    /*
    * 使用activiti提供的默认方式来创建mysql表
    * */
    @Test
    public void testCreateDbTable(){
//        需要使用activiti提供的工具类 ProcessEngine,使用方法getDefaultProcessEngine
//        getDefaultProcessEngine会默认从resources下读取名字为activiti.cfg.xml的文件
//        创建processEngine时，就会创建mysql的表

//        默认方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

//        RepositoryService repositoryService = processEngine.getRepositoryService();
        System.out.println(processEngine);
    }

}
