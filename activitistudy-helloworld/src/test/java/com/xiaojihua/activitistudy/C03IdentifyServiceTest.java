package com.xiaojihua.activitistudy;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class C03IdentifyServiceTest {

	// 创建一个Rule对象使用默认activiti 配置文件、自动创建processEngine引擎对象，使用H2数据库
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	/**
	 * 测试用户管理API
	 */
	@Test
	public void testUserAPI() {
		// 1、获取IdentifiService实例
		IdentityService identityService = activitiRule.getIdentityService();
		// 2、创建用户
		User user = identityService.newUser("zhangsan");
		user.setFirstName("san");
		user.setLastName("zhang");
		user.setEmail("zhangsan@163.com");
		// 3、保存用户到数据库
		identityService.saveUser(user);
		// 4、验证用户是否创建成功
		User userInDb = identityService.createUserQuery().userId("zhangsan").singleResult();
		assertNotNull(userInDb);
		// 5、删除用户
		identityService.deleteUser("zhangsan");
		// 6、验证是否删除成功
		userInDb = identityService.createUserQuery().userId("zhangsan").singleResult();
		assertNull(userInDb);
	}

	/**
	 * 测试组
	 */
	@Test
	public void testGroupAPI() {
		// 1、获取IdentitiService实例
		IdentityService identityService = activitiRule.getIdentityService();
		// 2、创建组
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		//
		group.setType("assignment");

		// 保存组
		identityService.saveGroup(group);

		// 验证组是否已保存成功，首先需要创建组查询对象
		List<Group> groupList = identityService.createGroupQuery().groupId("deptLeader").list();
		assertEquals(1, groupList.size());

		// 删除组
		identityService.deleteGroup("deptLeader");

		// 验证是否删除成功
		groupList = identityService.createGroupQuery().groupId("deptLeader").list();
		assertEquals(0, groupList.size());
	}

	/**
	 * 测试用户与组之间的关系
	 */
	@Test
	public void testUserAndGroupMemership() {
		// 1、获取IdentifiService实例
		IdentityService identityService = activitiRule.getIdentityService();
		// 2、创建并保存组对象
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);

		// 3、创建并保存用户对象
		User user = identityService.newUser("henryyan");
		user.setFirstName("Henry");
		user.setLastName("Yan");
		user.setEmail("yanhonglei@gmail.com");
		identityService.saveUser(user);

		//4、 把用户henryyan加入到组deptLeader中
		identityService.createMembership("henryyan", "deptLeader");

		// 5、查询属于组deptLeader的用户
		User userInGroup = identityService.createUserQuery().memberOfGroup("deptLeader").singleResult();
		assertNotNull(userInGroup);
		assertEquals("henryyan", userInGroup.getId());

		// 6、查询henryyan所属组
		Group groupContainsHenryyan = identityService.createGroupQuery().groupMember("henryyan").singleResult();
		assertNotNull(groupContainsHenryyan);
		assertEquals("deptLeader", groupContainsHenryyan.getId());
	}
}
