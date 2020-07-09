package com.xiaojihua.activitistudy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import static org.junit.Assert.*;

public class VerySimpleLeaveProcessTest {

	@Test
	public void testStartProcess() throws Exception {
		// 创建流程引擎，使用内存数据库
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();

		// 获取RepositoryService并部署流程定义文件
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String processFileName = "sayhelloleave.bpmn";
		repositoryService.createDeployment().addClasspathResource(processFileName).deploy();

		// 验证已部署流程定义是否成功
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		assertEquals("leavesayhello", processDefinition.getKey());

		// 获取RuntimeService，并启动流程并返回流程实例
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leavesayhello");
		assertNotNull(processInstance);
		// 输出流程实例的ID和流程定义的ID
		// pid:流程实例在数据库中的ID，pdid:由一系列参数组合并以冒号分割，如：leavesayhello:1:3 其中leavesayhello为流程定义的key(<process id="leavesayhello">),1为版本号，4为流程定义在数据库中的id
		System.out.println("pid=" + processInstance.getId() + ", pdid=" + processInstance.getProcessDefinitionId());
	}
}