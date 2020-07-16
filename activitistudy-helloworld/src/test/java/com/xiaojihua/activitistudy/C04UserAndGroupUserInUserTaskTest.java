package com.xiaojihua.activitistudy;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class C04UserAndGroupUserInUserTaskTest extends AbstractTest {

	@Before
	public void setUp() throws Exception {

		// 初始化7大Service实例
		super.setUp();

		// 创建并保存组对象
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);

		// 创建并保存用户对象
		User user = identityService.newUser("henryyan");
		user.setFirstName("Henry");
		user.setLastName("Yan");
		user.setEmail("yanhonglei@gmail.com");
		identityService.saveUser(user);

		// 把用户henryyan加入到组deptLeader中
		identityService.createMembership("henryyan", "deptLeader");
	}

	/**
	 * 每个方法执行之后清理用户与组
	 */
	@After
	public void afterInvokeTestMethod() throws Exception {
		//删除关系
		identityService.deleteMembership("henryyan", "deptLeader");
		//删除组
		identityService.deleteGroup("deptLeader");
		//删除用户
		identityService.deleteUser("henryyan");
	}

	/**
	 * 测试用户、组。
	 * userAndGroupInUserTask.bpmn测试的一个用户任务，任务的候选组是deptLeader，
	 * 而 用户henryyan是组中的一个用户，所以也能收到
	 * @throws Exception
	 */
	@Test
	@Deployment(resources = { "userAndGroupInUserTask.bpmn" })
	public void testUserAndGroupInUserTask() throws Exception {

		// 启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
		assertNotNull(processInstance);

		// 根据角色查询任务
		Task task = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
		taskService.claim(task.getId(), "henryyan");
		taskService.complete(task.getId());
	}

	/**
	 *  测试多个用户属于一个组，一个用户签收任务后，另一个用户就看不到了。
	 * @throws Exception
	 */
	@Test
//	@Deployment(resources = { "chapter5/userAndGroupInUserTask.bpmn" })
	@Deployment(resources = { "userAndGroupInUserTask.bpmn" })
	public void testUserTaskWithGroupContainsTwoUser() throws Exception {

		// 在setUp()的基础上再添加一个用户然后加入到组deptLeader中
		User user = identityService.newUser("jackchen");
		user.setFirstName("Jack");
		user.setLastName("Chen");
		user.setEmail("jackchen@gmail.com");
		identityService.saveUser(user);

		// 把用户henryyan加入到组deptLeader中
		identityService.createMembership("jackchen", "deptLeader");

		// 启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
		assertNotNull(processInstance);

		// jackchen作为候选人的任务
		Task jackchenTask = taskService.createTaskQuery().taskCandidateUser("jackchen").singleResult();
		assertNotNull(jackchenTask);

		// henryyan作为候选人的任务
		Task henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
		assertNotNull(henryyanTask);

		// jackchen签收任务
		taskService.claim(jackchenTask.getId(), "jackchen");

		// 再次查询用户henryyan是否拥有刚刚的候选任务
		henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
		assertNull(henryyanTask);
	}
}
