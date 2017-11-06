package cn.zsza.hello;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class CreateTable {
	@Test
	public void testCreateTable() {
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration
				.setJdbcUrl("jdbc:mysql://172.16.181.129:3306/activiti?useUnicode=true&characterEncoding=utf8");
		configuration.setJdbcUsername("root");
		configuration.setJdbcPassword("hadoop");


		/**
		 * public static final String DB_SCHEMA_UPDATE_FALSE =
		 * "false";//如果不存在表就抛出异常
		 *
		 * public static final String DB_SCHEMA_UPDATE_CREATE_DROP =
		 * "create-drop";每次都先删除表，再创建
		 *
		 * public static final String DB_SCHEMA_UPDATE_TRUE =
		 * "true";//如果不存在表就创建，存在就直接使用
		 */
		configuration
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);

		ProcessEngine processEngine = configuration.buildProcessEngine();
		System.out.println(processEngine);
	}

	@Test
	public void testCreateTableFromResource() {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"activiti.cfg.xml")//
				.buildProcessEngine();
		System.out.println(processEngine);
	}
}
