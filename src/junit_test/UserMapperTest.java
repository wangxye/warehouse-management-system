package junit_test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zzw.dao.UserMapper;
import cn.zzw.pojo.User;


public class UserMapperTest {
	private ApplicationContext applicatonContext;
	
	@Before
	public void setUp() throws Exception{
		String configLocation = "classpath:ApplicationContext-dao.xml";
		applicatonContext = new ClassPathXmlApplicationContext(configLocation);
	}
		
	@Test
	public void testFindUserById() throws Exception{
		UserMapper userMapper = (UserMapper)applicatonContext.getBean("userMapper");
		User user = userMapper.findUser("31f6b28b-1e9b-4f15-823e-0d45be0db604");
		System.out.println(user);
	}
    @Test
    public void testadd() {
    	UserMapper userMapper = (UserMapper)applicatonContext.getBean("userMapper");
    	User user  =new User();
    	user.setId("123");
    	user.setAddress("123");
    	user.setPassword("123");
    	user.setPhone("123123");
    	user.setName("123");
    	user.setIid("123");
    	user.setEmail("1231");
    	user.setUsername("13213");
    	userMapper.addUser(user);
    }
  @Test
    public void testupdate() {
    	UserMapper userMapper = (UserMapper)applicatonContext.getBean("userMapper");
    	User user  =new User();
    	user.setId("123");
    	user.setAddress("123");
    	user.setPassword("123");
    	user.setPhone("123123");
    	user.setName("123");
    	user.setIid("123");
    	user.setEmail("456464564564");
    	user.setUsername("13213");
    	userMapper.updateUser(user);
    }
    @Test 
    public  void testlogin() {
    	UserMapper userMapper = (UserMapper)applicatonContext.getBean("userMapper");
    	User user =userMapper.login("13213","123");
    	System.out.println(user);
    }
}
