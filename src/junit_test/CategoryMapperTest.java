package junit_test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zzw.dao.CategoryMapper;
import cn.zzw.pojo.Category;

public class CategoryMapperTest {

	private ApplicationContext applicatonContext;

	@Before
	public void setUp() throws Exception {
		String configLocation = "classpath:ApplicationContext-dao.xml";
		applicatonContext = new ClassPathXmlApplicationContext(configLocation);
	}

	@Test
	public void testadd() throws Exception {
		CategoryMapper categoryMapper = (CategoryMapper) applicatonContext.getBean("categoryMapper");
		Category c = new Category();
		c.setDescription("1231231");
		c.setId("1231321");
		c.setName("asdd");
		categoryMapper.addCategory(c);
	}

	@Test
	public void testdelete() {
		CategoryMapper categoryMapper = (CategoryMapper)applicatonContext.getBean("categoryMapper");
		categoryMapper.deleteCategory("1231321");
	}
	@Test
	public void testupdate() {
		CategoryMapper categoryMapper = (CategoryMapper)applicatonContext.getBean("categoryMapper");
		Category c = new Category();
		c.setDescription("1231231");
		c.setId("1231321");
		c.setName("aaaaaa");
		categoryMapper.updateCategory(c);
	}
	@Test
	public void getAll() {
		CategoryMapper categoryMapper = (CategoryMapper)applicatonContext.getBean("categoryMapper");
		categoryMapper.getAllCategory();
	}
}
