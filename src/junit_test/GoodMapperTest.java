package junit_test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zzw.dao.GoodMapper;
import cn.zzw.pojo.Category;
import cn.zzw.pojo.UpdateGoodVo;
import cn.zzw.pojo.good;

public class GoodMapperTest {

	private ApplicationContext applicatonContext;

	@Before
	public void setUp() throws Exception {
		String configLocation = "classpath:ApplicationContext-dao.xml";
		applicatonContext = new ClassPathXmlApplicationContext(configLocation);
	}

	@Test
	public void testadd() throws Exception {
		GoodMapper GoodMapper = (GoodMapper) applicatonContext.getBean("goodMapper");
		good good = new good();
		Category category = new Category();
		good.setCategory(category);
		good.setDescription("1231");
		good.setId("1789");
		good.setIid("112");
		good.setImage("156565");
		good.setQuantity(156);
		good.setName("16");
		good.setLocation("15");
		GoodMapper.addGood(good);
	}

	@Test
	public void testdelete() {
		GoodMapper GoodMapper = (GoodMapper) applicatonContext.getBean("goodMapper");
		GoodMapper.deleteGood("1789");
	}
	@Test
	public void testupdate() {
		GoodMapper GoodMapper = (GoodMapper) applicatonContext.getBean("goodMapper");
		UpdateGoodVo good = new UpdateGoodVo();		
		good.setCategory_id("1");
		good.setDescription("1231");
		good.setId("1789");
		good.setIid("11111111111111");
		good.setImage("156565");
		good.setQuantity(156);
		good.setName("16");
		good.setLocation("15");
		GoodMapper.updateGood(good);
	}
	@Test 
	public void testgoodview() {
		GoodMapper GoodMapper = (GoodMapper) applicatonContext.getBean("goodMapper");
		GoodMapper.findGood("021e7e3b-5368-4a91-ba2e-d22a11274b1c");
	}
	@Test
	public void testfindforcategory() {
		GoodMapper GoodMapper = (GoodMapper) applicatonContext.getBean("goodMapper");
		GoodMapper.findforCategory("57edcb57-7c73-4893-b3dc-fcf6f2e32570");
	}
	@Test
	public void testgetAll() {
		GoodMapper GoodMapper = (GoodMapper) applicatonContext.getBean("goodMapper");
		GoodMapper.getAllGood();
	}
}
