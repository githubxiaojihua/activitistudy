package me.kafeitu.activiti.web.chapter13;

import me.kafeitu.activiti.chapter13.Page;
import me.kafeitu.activiti.chapter13.PageUtil;
import me.kafeitu.activiti.chapter6.util.UserUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.NativeExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运行中的执行实例Execution
 * User: henryyan
 */
@Controller
@RequestMapping("/chapter13")
public class ExecutionController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    /**
     * 用户参与过的流程
     *
     * @return
     */
    @RequestMapping("execution/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("chapter13/execution-list");
    /* 标准查询
    List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
    List<Execution> list = runtimeService.createExecutionQuery().list();
    mav.addObject("list", list);
    */

        User user = UserUtil.getUserFromSession(request.getSession());
        Page<Execution> page = new Page<Execution>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);
        NativeExecutionQuery nativeExecutionQuery = runtimeService.createNativeExecutionQuery();

		/*
		 * native query 将ACT_RU_EXECUTION和ACT_HI_TASKINST做关联
		 *     在ACT_RU_EXECUTION中查询所有处于活动状态的流程实例（其中也可能是执行实例，因为有可能是有子流程或调用活动）
		 *     这种情况下父流程实例的act_id是空的且is_active=false，而相应子流程或调用活动的act_id不为空且 is_active=true。
		 *     然后根据PROC_INST_ID将两表关联。Proc_inst_id这个字段代表着当前主流程实例，Execution_id代表执行实例，
		 *     如果没有子流程或调用活动这两者的值是一样的，这两个值是一对多的关系。在ACT_RU_EXECUTION和ACT_HI_TASKINST
		 *     两个表中都有这两个字段，可以统一查询某一流程实例的所有执行实例和活动。
		 */
        String sql = "select RES.* from ACT_RU_EXECUTION RES left join ACT_HI_TASKINST ART on ART.PROC_INST_ID_ = RES.PROC_INST_ID_ "
                + " where ART.ASSIGNEE_ = #{userId} and ACT_ID_ is not null and IS_ACTIVE_ = 'TRUE' order by START_TIME_ desc";

        nativeExecutionQuery.parameter("userId", user.getId());

        List<Execution> executionList = nativeExecutionQuery.sql(sql).listPage(pageParams[0], pageParams[1]);

        // 查询流程定义对象
        Map<String, ProcessDefinition> definitionMap = new HashMap<String, ProcessDefinition>();

        // 任务的英文-中文对照
        Map<String, Task> taskMap = new HashMap<String, Task>();

        // 每个Execution的当前活动ID，可能为多个
        //每一个Execution可能对应多个活动ID。 流程实例和执行实例是一对多的关系，执行实例和活动ID也是一对多的关系。
        //活动ID指的是流程定义中定义的活动ID，但是一条Execution只对应一个act_id
        Map<String, List<String>> currentActivityMap = new HashMap<String, List<String>>();

        // 设置每个Execution对象的当前活动节点
        for (Execution execution : executionList) {
            ExecutionEntity executionEntity = (ExecutionEntity) execution;
            String processInstanceId = executionEntity.getProcessInstanceId();
            String processDefinitionId = executionEntity.getProcessDefinitionId();

            // 缓存ProcessDefinition对象到Map集合
            definitionCache(definitionMap, processDefinitionId);

            // 查询当前流程的所有处于活动状态的活动ID，如果并行的活动则会有多个
            List<String> activeActivityIds = runtimeService.getActiveActivityIds(execution.getId());
            currentActivityMap.put(execution.getId(), activeActivityIds);
            
            //根据活动ID查询对应的TASK
            for (String activityId : activeActivityIds) {

                //根据活动ID及执行实例ID，查询处于活动状态的任务
                Task task = taskService.createTaskQuery().taskDefinitionKey(activityId).executionId(execution.getId()).singleResult();

                // 调用活动。根据调用活动的数据结构，在Execution表中会有三条记录，通过上面的语句查询到在只是处于中间的那条
                //也就是主流程调用调用活动时自动创建的那条记录，通过它是查不到对应的任务的，因为具体的任务是由调用活动中的任务定义
                //产生的。需要根据super_exec来查询对调用活动对应的流程实例，然后根据流程实例查询到任务
                if (task == null) {
                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                            .superProcessInstanceId(processInstanceId).singleResult();
                    task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
                    definitionCache(definitionMap, processInstance.getProcessDefinitionId());
                }
                taskMap.put(activityId, task);
            }
        }

        mav.addObject("taskMap", taskMap);
        mav.addObject("definitions", definitionMap);
        mav.addObject("currentActivityMap", currentActivityMap);

        page.setResult(executionList);
        page.setTotalCount(nativeExecutionQuery.sql("select count(*) from (" + sql + ")").count());
        mav.addObject("page", page);

        return mav;
    }

    /**
     * 流程定义对象缓存
     *
     * @param definitionMap
     * @param processDefinitionId
     */
    private void definitionCache(Map<String, ProcessDefinition> definitionMap, String processDefinitionId) {
        if (definitionMap.get(processDefinitionId) == null) {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            processDefinitionQuery.processDefinitionId(processDefinitionId);
            ProcessDefinition processDefinition = processDefinitionQuery.singleResult();

            // 放入缓存
            definitionMap.put(processDefinitionId, processDefinition);
        }
    }
}
