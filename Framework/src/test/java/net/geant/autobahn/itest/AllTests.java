package net.geant.autobahn.itest;

import net.geant.autobahn.itest.env1.AllEnv1Tests;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AllEnv1Tests.class
})
public class AllTests {

	@BeforeClass
	public static void initialize() {
		LogUtils.setLoggerClass(Log4jLogger.class);
	}
	
}
