package com.xiaojihua.activitistudy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import static org.junit.Assert.*;

public class C05ClasspathDeploymentTest extends AbstractTest {
	@Test
	public void testClasspathDeployment() throws Exception {

		// 定义classpath
		String bpmnClasspath = "chapter5/userAndGroupInUserTask.bpmn";
		String pngClasspath = "chapter5/userAndGroupInUserTask.png";

		// 创建部署构建器
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

		// 添加资源
		// 添加流程定义文件
		deploymentBuilder.addClasspathResource(bpmnClasspath);
		// 添加对应的图片资源文件
		deploymentBuilder.addClasspathResource(pngClasspath);

		// 执行部署
		deploymentBuilder.deploy();

		// 验证流程定义是否部署成功
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		long count = processDefinitionQuery.processDefinitionKey("userAndGroupInUserTask").count();
		assertEquals(1, count);

		// 读取对应的图片文件
		ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
		String diagramResourceName = processDefinition.getDiagramResourceName();
		assertEquals(pngClasspath, diagramResourceName);
	}

}
