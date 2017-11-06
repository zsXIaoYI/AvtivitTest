package cn.zsza.hello;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 *
 */
public class Hello {
	
	//创建工作流的核心对象（流程引擎）
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**部署流程定义（classpath）*/
	@Test
	public void deployementProcessDefinition(){
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
					.createDeployment()//创建部署对象
					.name("test请假流程")//指定部署名称
					.addClasspathResource("test/hello.bpmn")//从类路径加载资源文件，一次只能加载一个文件
					.addClasspathResource("test/hello.png")//从类路径加载资源文件，一次只能加载一个文件
					.deploy();//完成部署
		/**
		 * ACT_RE_DEPLOYMENT 表中ID_字段 22501
		 */
		System.out.println("部署ID："+deployment.getId());
		/**
		 * ACT_RE_DEPLOYMENT 表中NAME_字段 test请假流程
		 */
		System.out.println("部署名称："+deployment.getName());
	}
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance(){
		//使用流程定义的key启动流程实例(对应hello.bpmn文件中的流程中的id属性)
		String processDefinitionKey = "helloworld";
		ProcessInstance pi = processEngine.getRuntimeService()//管理执行对象和流程实例相关的Service（正在执行）
					.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，因为当使用流程定义key的时候，默认启动的是最新版本

		/**
		 * ACT_RU_TASK表中PROC_INST_ID_字段 25001
		 */
		System.out.println("流程实例ID："+pi.getId());
		/**
		 * ACT_RU_TASK表中PROC_DEF_ID_字段 helloworld:1:22504
		 */
		System.out.println("流程定义ID："+pi.getProcessDefinitionId());
		
	}
	

	
	/**完成任务*/
	@Test
	public void completeTask(){
		//任务ID
		String taskId = "302";
		processEngine.getTaskService()//与任务相关的Service（正在执行）
						.complete(taskId);
		System.out.println("完成任务：任务ID："+taskId);
	}
}
