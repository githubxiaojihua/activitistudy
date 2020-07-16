package com.xiaojihua.activitistudy.chapter6;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import static org.junit.Assert.*;

import com.xiaojihua.activitistudy.AbstractTest;

public class LeaveDynamicFormTest extends AbstractTest {

	/**
	 * 部门领导和人事全部审批通过
	 */
	@Test
	@Deployment(resources = "chapter6/dynamic-form/leave.bpmn")
	public void allApproved() throws Exception {

		// 1、验证是否部署成功
		long count = repositoryService.createProcessDefinitionQuery().count();
		assertEquals(1, count);

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("leave").singleResult();

		// 2、设置当前用户
		String currentUserId = "henryyan";
		identityService.setAuthenticatedUserId(currentUserId);

		// 3、设置启动动态表单参数并启动流程
		Map<String, String> variables = new HashMap<String, String>();
		// 初始化开始和结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		String startDate = sdf.format(ca.getTime());
		ca.add(Calendar.DAY_OF_MONTH, 2); // 当前日期加2天
		String endDate = sdf.format(ca.getTime());
		// 设置参数
		variables.put("startDate", startDate);
		variables.put("endDate", endDate);
		variables.put("reason", "公休");
		// 启动流程，此处通过submitStartFormDat的方法进行启动，作用与runtimeService.startProcessInstanceByKey
		// 一样都是启动流程，不同的是submitStartFormDat会将第二个参数中的变量赋值到启动事件的动态表单中
		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variables);
		assertNotNull(processInstance);

		// 4、获取部门领导的审批任务并 审批通过
		Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		variables = new HashMap<String, String>();
		variables.put("deptLeaderApproved", "true");
		// 提交任务表单，并完成任务，此处与taskService.complete类似
		formService.submitTaskFormData(deptLeaderTask.getId(), variables);

		// 5、人事审批通过
		Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
		variables = new HashMap<String, String>();
		variables.put("hrApproved", "true");
		formService.submitTaskFormData(hrTask.getId(), variables);

		// 6、销假（根据申请人的用户ID读取）
		Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
		variables = new HashMap<String, String>();
		variables.put("reportBackDate", sdf.format(ca.getTime()));
		formService.submitTaskFormData(reportBackTask.getId(), variables);

		// 7、验证流程是否已经结束
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished()
				.singleResult();
		assertNotNull(historicProcessInstance);

		// 8、读取历史变量
		Map<String, Object> historyVariables = packageVariables(processInstance);

		// 验证执行结果
		assertEquals("ok", historyVariables.get("result"));
	}

	/**
	 * 领导驳回后申请人取消申请
	 */
	@Test
	@Deployment(resources = "chapter6/dynamic-form/leave.bpmn")
	public void cancelApply() throws Exception {

		// 1、设置当前用户
		String currentUserId = "henryyan";
		identityService.setAuthenticatedUserId(currentUserId);

		// 2、获取部署后的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("leave").singleResult();
		// 设置动态表单参数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> variables = new HashMap<String, String>();
		Calendar ca = Calendar.getInstance();
		String startDate = sdf.format(ca.getTime());
		ca.add(Calendar.DAY_OF_MONTH, 2);
		String endDate = sdf.format(ca.getTime());

		variables.put("startDate", startDate);
		variables.put("endDate", endDate);
		variables.put("reason", "公休");
		// 3、提交表单数据 并启动流程
		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variables);
		assertNotNull(processInstance);

		// 3、部门领导审批不通过
		Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		variables = new HashMap<String, String>();
		variables.put("deptLeaderApproved", "false");
		formService.submitTaskFormData(deptLeaderTask.getId(), variables);

		// 4、调整申请
		Task modifyApply = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
		variables = new HashMap<String, String>();
		variables.put("reApply", "false");
		variables.put("startDate", startDate);
		variables.put("endDate", endDate);
		variables.put("reason", "公休");
		// reApply为false所以会走到结束节点，整个流程结束
		formService.submitTaskFormData(modifyApply.getId(), variables);
		
		// 读取历史变量
		Map<String, Object> historyVariables = packageVariables(processInstance);

		// 验证执行结果
		assertEquals("canceled", historyVariables.get("result"));

	}

	/**
	 * 读取历史变量并封装到Map中 注意：输出结果来看，有的变量输出了两次，一次是表单中的字段，另一次是保存表单字段的时候会将表单字段进行类型转换
	 * 然后以表单字段ID为名称保存到数据库中 前提是设置引擎的history属性为full
	 */
	private Map<String, Object> packageVariables(ProcessInstance processInstance) {
		Map<String, Object> historyVariables = new HashMap<String, Object>();
		List<HistoricDetail> list = historyService.createHistoricDetailQuery()
				.processInstanceId(processInstance.getId()).list();
		for (HistoricDetail historicDetail : list) {
			if (historicDetail instanceof HistoricFormProperty) {
				// 表单中的字段
				HistoricFormProperty field = (HistoricFormProperty) historicDetail;
				historyVariables.put(field.getPropertyId(), field.getPropertyValue());
				System.out.println("form field: taskId=" + field.getTaskId() + ", " + field.getPropertyId() + " = "
						+ field.getPropertyValue());
			} else if (historicDetail instanceof HistoricVariableUpdate) {
				// 普通的变量
				HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
				historyVariables.put(variable.getVariableName(), variable.getValue());
				System.out.println("variable: " + variable.getVariableName() + " = " + variable.getValue());
			}
		}
		return historyVariables;
	}
}
