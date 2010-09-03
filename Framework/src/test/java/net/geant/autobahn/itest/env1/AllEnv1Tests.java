package net.geant.autobahn.itest.env1;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	AutobahnTest.class, StartingStoppingTests.class, TestsWithTool.class
})
public class AllEnv1Tests {

	@BeforeClass
	public static void initialize() {
		System.out.println("Starting tests for env1");
		LogUtils.setLoggerClass(Log4jLogger.class);
	}
}
