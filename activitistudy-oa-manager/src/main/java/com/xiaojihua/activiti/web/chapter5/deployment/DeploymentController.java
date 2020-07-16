package com.xiaojihua.activiti.web.chapter5.deployment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xiaojihua.activiti.web.base.AbstractController;

@Controller
@RequestMapping("/chapter5")
public class DeploymentController extends AbstractController {

	/**
	 * 查询目前已经布置的流程
	 * @return
	 */
	@RequestMapping("/process-list")
	public ModelAndView processList() {
		//1、获取引擎对象,通过引擎对象获取RepositoryService
		RepositoryService repositoryService =  processEngine.getRepositoryService();
		//2、通过RepositoryService获取流程定义列表
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
		//3、设置ModelAndView数据并返回
		ModelAndView mav = new ModelAndView("chapter5/process-list");
		mav.addObject("processDefinitionList",processDefinitionList);
		return mav;
	}
	
	/**
	 * 发布流程定义文件，接收bar，zip,bpmn等形式
	 * @param file
	 * @return
	 */
	@RequestMapping("/deploy")
	public String deploy(@RequestParam("file") MultipartFile file) {
		try {
			//1、从MultipartFile中获取文件流
			InputStream fileStream = file.getInputStream();
			//2、获取文件名称
			String fileName = file.getOriginalFilename();
			String suffixName = FilenameUtils.getExtension(fileName);
			RepositoryService repositoryService =  processEngine.getRepositoryService();
			//3、根据文件后缀名走不同的deploy方式
			if("bar".equals(suffixName) || "zip".equals(suffixName)) {
				//4、使用压缩文件的方式发布
				repositoryService.createDeployment().addZipInputStream(new ZipInputStream(fileStream)).deploy();
			}else {
				//5、其它的使用普通的inputstream方式发布
				repositoryService.createDeployment().addInputStream(fileName, fileStream).deploy();
			}
		}catch(Exception e) {
			logger.error("发布出错：" + e.getStackTrace());
		}
		
		return "redirect:process-list";
	}
	
	/**
	 * 向浏览器输出资源内容
	 * @param pdid
	 * @param resourceName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/read-resouce")
	public void readResource(String pdid,String resourceName,HttpServletResponse response) throws IOException {
		//1、获取流程定义查询对象
		RepositoryService repositoryService =  processEngine.getRepositoryService();
		//2、根据processDefinitionId(流程定义ID)查询相应的流程定义对象
		ProcessDefinition definition =  repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
		//3、通过repositoryService接口读取资源文件并输出
		InputStream resourceInputStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while((len = resourceInputStream.read(b,0,1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	/**
	 * 删除资源
	 * @param deploymentId
	 * @return
	 */
	@RequestMapping("/delete-deployment")
	public String deleteProcessDefinition(String deploymentId) {
		//调用repositoryService的API删除部署的定义文件，第二个参数表示同时删除对应的数据
		repository.deleteDeployment(deploymentId,true);
		return "redirect:process-list";
	}
}
