package com.xiaojihua.activitistudy;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

/**
 * 抽象的测试类，提供关于Activiti测试的初始化配置
 * @author Administrator
 *
 */
public class AbstractTest {

	/**
	 * 专门用于测试套件
	 */
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");

	protected ProcessEngine processEngine;
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	protected HistoryService historyService;
	protected IdentityService identityService;
	protected ManagementService managementService;
	protected FormService formService;

	/**
	 * 开始测试
	 */
	@BeforeClass
	public static void setUpForClass() throws Exception {
		System.out.println("++++++++ 开始测试 ++++++++");
	}

	/**
	 * 结束测试
	 */
	@AfterClass
	public static void testOverForClass() throws Exception {
		System.out.println("-------- 结束测试 --------");
	}

	/**
	 * 初始化变量
	 */
	@Before
	public void setUp() throws Exception {
		processEngine = activitiRule.getProcessEngine();
		repositoryService = activitiRule.getRepositoryService();
		runtimeService = activitiRule.getRuntimeService();
		taskService = activitiRule.getTaskService();
		historyService = activitiRule.getHistoryService();
		identityService = activitiRule.getIdentityService();
		managementService = activitiRule.getManagementService();
		formService = activitiRule.getFormService();
	}
}
