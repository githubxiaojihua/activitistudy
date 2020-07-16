package com.xiaojihua.activiti.web.base;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaojihua.activiti.util.ActivitiUtils;

/**
 * 抽象Controller，提供一些基础的方法、属性
 * @author Administrator
 *
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    protected ProcessEngine processEngine = null;
    protected RepositoryService repository = null;

    public AbstractController() {
        super();
        processEngine = ActivitiUtils.getProcessEngine();
        repository = processEngine.getRepositoryService();
    }
}
