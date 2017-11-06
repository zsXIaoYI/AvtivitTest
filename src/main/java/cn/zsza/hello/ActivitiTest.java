package cn.zsza.hello;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;
import java.util.List;

/**
 * Created by zhangsong on 2017/11/6.
 */
public class ActivitiTest {

    /**
     * ACT_GE_BYTEARRAY
     */
    @Test
    public void testDeploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        Deployment deployment = processEngine
                .getRepositoryService()
                .createDeployment()
                .name("test请假流程")
                .addClasspathResource("test/hello.bpmn")
                .addClasspathResource("test/hello.png")
                .deploy();

        /**
         * ACT_RE_DEPLOYMENT 表中ID_字段 1
         */
        System.out.println("部署ID："+ deployment.getId());
        /**
         * ACT_RE_DEPLOYMENT 表中NAME_字段 test请假流程
         */
        System.out.println("部署名称："+ deployment.getName());
    }
    /**
     * 代码运行完
     * 请假申请 ACT_RU_TASK表生成一个"请假申请"的记录，其id（ID_）为2504，给部门经理审批
     *
     */
    @Test
    public void testStartProcessInstance(){
        String pdkey = "helloworld";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        processEngine.getRuntimeService()
                .startProcessInstanceByKey(pdkey);
    }
    /**
     * 查询我的个人任务
     */
    @Test
    public void findMyPersonalTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //任务办理人
        String assignee = "张三";
        List<Task> list = processEngine.getTaskService()//与任务相关的Service（正在执行）
                .createTaskQuery()//创建任务的查询对象
                .taskAssignee(assignee)//指定个人任务的办理人
                .list();
        if(list!=null && list.size()>0){
            for(Task task:list){
                /**
                 * ACT_RU_TASK表中"ID_"字段
                 */
                System.out.println("任务ID："+task.getId());
                /**
                 * ACT_RU_TASK表中"NAME_"字段
                 */
                System.out.println("任务名称："+task.getName());
                /**
                 * ACT_RU_TASK表中"CREATE_TIME_"字段
                 */
                System.out.println("任务的创建时间："+task.getCreateTime());
                /**
                 * ACT_RU_TASK表中"ASSIGNEE_"字段
                 */
                System.out.println("任务的办理人："+task.getAssignee());
                /**
                 * ACT_HI_PROCINST表中"ID_"字段（一个流程启动后，流程实例只有1个）
                 */
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                /**
                 * ACT_HI_PROCINST表中"PROC_INST_ID_"字段
                 */
                System.out.println("执行对象ID："+task.getExecutionId());
                /**
                 * ACT_HI_PROCINST表中"PROC_DEF_ID_"字段
                 */
                System.out.println("流程定义ID："+task.getProcessDefinitionId());//helloworld:1:4
            }
        }
    }
    /**
     *  部门经理审批 生成一个"部门经理审批"的记录，其id为5002，给总经理审批
     */
    @Test
    public void  testFinishTask_Applicator(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        processEngine.getTaskService()
                .complete("2504");
    }

    /**
     * 总经理审批 生成一个"总经理审批"的记录，其id为7502，结束
     */
    @Test
    public void testFinshTask_ManagerApprove(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("5002");
    }

    /**
     * 完成请假
     */
    @Test
    public void testFinshTask_BossApprove(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("7502");
    }
    @Test
    public void test1(){
        System.out.println(11);
    }
}
