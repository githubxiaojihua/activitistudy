package com.xiaojihua.activitistudy;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import static org.junit.Assert.*;

public class C09ZipStreamDeployment extends AbstractTest {
	@Test
    public void testZipStreamFromAbsoluteFilePath() throws Exception {
        // 从classpath读取资源并部署到引擎中
		// 读入为流文件
        InputStream zipStream = getClass().getClassLoader().getResourceAsStream("chapter5/chapter5.bar");
        // 通过API发布资源文件，引擎会先解压，然后逐个部署资源文件
        repositoryService.createDeployment().addZipInputStream(new ZipInputStream(zipStream)).deploy();

        // 统计已部署流程定义的数量
        long count = repositoryService.createProcessDefinitionQuery().count();
        // 引擎只会部署后缀为bpmn或是bpmn20.xml的流程定义文件，不会部署png的图片资源文件
        assertEquals(2, count);

        // 查询部署记录
        // 部署记录对象：一个deploy()方法对应一个Deployment，包含本次部署的ID（注意并不是资源的ID），部署的时间等
        // 并且决部署的资源也会关联所属的部署记录的ID
        Deployment deployment = repositoryService.createDeploymentQuery().singleResult();
        assertNotNull(deployment);
        // 获取本次部署记录对应的ID
        String deploymentId = deployment.getId();

        // 验证4个资源文件是否都已成功部署
        assertNotNull(repositoryService.getResourceAsStream(deploymentId, "sayhelloleave2.bpmn"));
        assertNotNull(repositoryService.getResourceAsStream(deploymentId, "sayhelloleave2.png"));
        assertNotNull(repositoryService.getResourceAsStream(deploymentId, "userAndGroupInUserTask.bpmn"));
        assertNotNull(repositoryService.getResourceAsStream(deploymentId, "userAndGroupInUserTask.png"));
    }
}
