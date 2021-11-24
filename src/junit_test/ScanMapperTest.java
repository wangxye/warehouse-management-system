package junit_test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zzw.dao.ScanMapper;

public class ScanMapperTest {

private ApplicationContext applicatonContext;
	
	@Before
	public void setUp() throws Exception{
		String configLocation = "classpath:ApplicationContext-dao.xml";
		applicatonContext = new ClassPathXmlApplicationContext(configLocation);
	}
		 
	@Test
	public void testgetAll() throws Exception{
		ScanMapper ScanMapper = (ScanMapper)applicatonContext.getBean("scanMapper");
		 ScanMapper.getAllScan();
		
	}
	@Test
	public void testgetMap() throws Exception{
		ScanMapper ScanMapper = (ScanMapper)applicatonContext.getBean("scanMapper");
		 ScanMapper.getMap();
		
	}
	

}
