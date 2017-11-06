package cn.zsza.hello;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
/**
 * Created by zhangsong on 2017/11/3.
 */
public class LeaveProcessTets {


    /**
     * ACT_GE_BYTEARRAY
     */
    @Test
    public void testDeploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        processEngine
                .getRepositoryService()
                .createDeployment()
                .addClasspathResource("diagrams/helloworld.bpmn")
                .addClasspathResource("diagrams/helloworld.png")
                .deploy();

    }
    /**
     * 代码运行完
     * 请假申请 ACT_RU_TASK表生成一个"请假申请"的记录，其id（ID_）为12504，给部门经理审批
     *
     */
    @Test
    public void testStartProcessInstance(){
        String pdkey = "itheima09";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        processEngine.getRuntimeService()
                .startProcessInstanceByKey(pdkey);
    }
    /**
     *  部门经理审批 生成一个"部门经理审批"的记录，其id为15002，给总经理审批
     */
    @Test
    public void  testFinishTask_Applicator(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        processEngine.getTaskService()
                .complete("12504");
    }

    /**
     * 总经理审批 生成一个"总经理审批"的记录，其id为17502，给总经理审批
     */
    @Test
    public void testFinshTask_ManagerApprove(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("15002");
    }

    /**
     * 完成请假
     */
    @Test
    public void testFinshTask_BossApprove(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("17502");
    }


}
