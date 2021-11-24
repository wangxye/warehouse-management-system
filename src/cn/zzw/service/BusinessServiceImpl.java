package cn.zzw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zzw.dao.CategoryMapper;
import cn.zzw.dao.GoodMapper;
import cn.zzw.dao.ScanMapper;
import cn.zzw.dao.UserMapper;
import cn.zzw.pojo.Category;
import cn.zzw.pojo.Coordinate;
import cn.zzw.pojo.Scan;
import cn.zzw.pojo.UpdateGoodVo;
import cn.zzw.pojo.User;
import cn.zzw.pojo.good;
import cn.zzw.pojo.goodview;
@Service
public class BusinessServiceImpl implements BusinessService {

	
	@Autowired
	 private  CategoryMapper   categoryMapper;
	@Autowired
	 private  GoodMapper goodMapper;
	@Autowired
	 private  ScanMapper scanMapper;
	@Autowired
	 private  UserMapper userMapper;
	//分类
	@Override
	public void addCategory(Category c) {
		 categoryMapper.addCategory(c);
	}

	@Override
	public void deleteCategory(String id) {  
		categoryMapper.deleteCategory(id);
	}

	@Override
	public Category findCategory(String id) {	
		return  categoryMapper.findCategory(id);
	}

	@Override
	public void updateCategory(Category category) {
		categoryMapper.updateCategory(category);
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryMapper.getAllCategory();
	}

	//货物
	@Override
	public void addGood(good g) {
		goodMapper.addGood(g);
	}

	@Override
	public void deleteGood(String id) {
		goodMapper.deleteGood(id);
	}

	@Override
	public void updateGood(UpdateGoodVo good) {
		goodMapper.updateGood(good);
	}

	@Override
	public goodview findGood(String id) {
		return goodMapper.findGood(id);
	}

	@Override
	public List<good> findforCategory(String category_id) {
		return goodMapper.findforCategory(category_id);
	}

	@Override
	public List<good> getAllGood() {
		return goodMapper.getAllGood();
	}

	//货物扫描
	@Override
	public List<Scan> getAllScan() {
		return scanMapper.getAllScan();
	}
	
	@Override
	public List<Scan> getRecentScan() {
		// TODO 自动生成的方法存根
		return scanMapper.getRecentScan();
	}
	
	@Override
	public Coordinate getMap() {	
		return scanMapper.getMap();
	}


	//用户
	@Override
	public void addUser(User user) {
	   userMapper.addUser(user);
	}

	@Override
	public User findUser(String id) {
		return userMapper.findUser(id);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public User login(String username, String password) {
		return userMapper.login(username, password);
	}

	@Override
	public List<User> findAllUser() {	
		return userMapper.findAllUser();
	}

}
