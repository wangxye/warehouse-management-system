package cn.zzw.dao;

import java.util.List;

import cn.zzw.pojo.Category;

public interface CategoryMapper {
	public void addCategory(Category c);
	public void deleteCategory(String  id);
	public Category findCategory(String id);
	public  void updateCategory(Category category);
	public List<Category> getAllCategory();
}
