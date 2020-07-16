package com.xiaojihua.activitistudy;

import java.io.FileInputStream;

import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import static org.junit.Assert.*;

public class C07InputStreamDeployment extends AbstractTest {

	/**
     * 从具体的文件中读取输入流部署
     */
    @Test
    public void testInputStreamFromAbsoluteFilePath() throws Exception {

        // 注意：读者根据自己的实际项目路径更改后验证
        String filePath = "I:\\activitistudy\\activitistudy\\activitistudy-helloworld\\src\\main\\resources\\chapter5\\userAndGroupInUserTask.bpmn";

        // 读取classpath的资源为一个输入流
        FileInputStream fileInputStream = new FileInputStream(filePath);
        repositoryService.createDeployment().addInputStream("userAndGroupInUserTask.bpmn", fileInputStream).deploy();

        // 验证是否部署成功
        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
        long count = pdq.processDefinitionKey("userAndGroupInUserTask").count();
        assertEquals(1, count);
    }
}
