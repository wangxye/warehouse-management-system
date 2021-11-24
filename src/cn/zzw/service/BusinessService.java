package cn.zzw.service;

import java.util.List;

import cn.zzw.pojo.Category;
import cn.zzw.pojo.Coordinate;
import cn.zzw.pojo.Scan;
import cn.zzw.pojo.UpdateGoodVo;
import cn.zzw.pojo.User;
import cn.zzw.pojo.good;
import cn.zzw.pojo.goodview;

public interface BusinessService {
	
	//����
	public void addCategory(Category c);
	public void deleteCategory(String  id);
	public Category findCategory(String id);
	public  void updateCategory(Category category);
	public List<Category> getAllCategory();
	
	//����
	public void addGood(good g);
	public  void deleteGood(String id);
	public  void updateGood( UpdateGoodVo good) ;
	public goodview findGood(String id) ;
	public List<good> findforCategory(String category_id);
	public List<good> getAllGood() ;
	
	//ɨ��
	public List<Scan> getAllScan();
	public List<Scan> getRecentScan();
	public Coordinate getMap();
	
	
	//�û�
	public void addUser(User user);
	public User findUser(String id);
	public List<User> findAllUser();
	public void updateUser(User user);
	public User login(String username,String password);
	
}
