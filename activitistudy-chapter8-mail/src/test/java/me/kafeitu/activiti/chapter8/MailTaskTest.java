package me.kafeitu.activiti.chapter8;import org.activiti.engine.runtime.ProcessInstance;import org.activiti.engine.test.ActivitiRule;import org.activiti.engine.test.Deployment;import org.junit.Rule;import org.junit.Test;import java.util.HashMap;import java.util.Map;import static org.junit.Assert.assertNotNull;/** * 通过Activiti发送邮件 * * @author henryyan */public class MailTaskTest {    @Rule    public ActivitiRule activitiRule = new ActivitiRule("activiti-mail.cfg.xml");    @Test    @Deployment(resources = {"diagrams/chapter8/testMailTask.bpmn"})    public void sendMailLocalTest() throws Exception {        Map<String, Object> variables = new HashMap<String, Object>();        variables.put("name", "Tom Wang");        //这里使用自建邮件服务器James中默认的用户名并发给自己        variables.put("to", "user01@james.local");        variables.put("from", "user01@james.local");        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("testMailTask", variables);        assertNotNull(processInstance);    }}