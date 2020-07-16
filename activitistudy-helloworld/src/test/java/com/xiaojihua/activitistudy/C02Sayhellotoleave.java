package com.xiaojihua.activitistudy;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import static org.junit.Assert.*;

public class C02Sayhellotoleave {

	@Test
	public void testStartProcess() throws Exception {
		// 创建流程引擎，使用内存数据库
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();
		// 获取RepositoryService并部署流程定义文件
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String bpmnFileName = "sayhelloleave2.bpmn";
		// 引入定义文件方式一 activiti 5.9版本之前只支持bpmn.xml的文件，之后换为.bpmn
		repositoryService.createDeployment().addInputStream("sayhelloleave2.bpmn",
				this.getClass().getClassLoader().getResourceAsStream(bpmnFileName)).deploy();
		// 引入定义文件方式二（与C01一样）
//		repositoryService.createDeployment().addClasspathResource(bpmnFileName).deploy();
		// 验证已部署流程定义是否成功
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		assertEquals("SayHelloToLeave", processDefinition.getKey());
		
		// 获取RuntimeService，并启动流程并返回流程实例
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 设置流程运转过程中的参数
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applyUser", "employee1");//请假人员
		variables.put("days", 3);//请假天数
		// 启动流程并传递参数，参数会存储到数据库中，后期可以通过相关接口读取
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("SayHelloToLeave", variables);
		assertNotNull(processInstance);
		System.out.println("pid=" + processInstance.getId() + ", pdid=" + processInstance.getProcessDefinitionId());

		// 查询组（Group） deptLeader的未签收任务，并验证任务的名称
		TaskService taskService = processEngine.getTaskService();
		Task taskOfDeptLeader = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		assertNotNull(taskOfDeptLeader);
		assertEquals("领导审批", taskOfDeptLeader.getName());
		// 调用taskService的claim方法对此任务进行签收，此时任务只归leaderUser所有。
		taskService.claim(taskOfDeptLeader.getId(), "leaderUser");
		
		// 领导的处理结果，流程启动的时候传入了请假人员和天数来模拟实际场景，领导审批通过对应的是approved put 为true。
		variables = new HashMap<String, Object>();
		variables.put("approved", true);
		// 设置任务完成，并将处理结果设置为流程变量
		taskService.complete(taskOfDeptLeader.getId(), variables);

		// 任务已经完成，再次查询deptLeader下的任务，结果为空
		taskOfDeptLeader = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		assertNull(taskOfDeptLeader);

		// 通过流程引擎获取历史记录查询接口
		HistoryService historyService = processEngine.getHistoryService();
		// 统计已经完成的流程数量，并验证结果
		long count = historyService.createHistoricProcessInstanceQuery().finished().count();
		assertEquals(1, count);
	}

}
