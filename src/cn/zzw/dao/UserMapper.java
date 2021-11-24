package cn.zzw.dao;

import java.util.List;

import cn.zzw.pojo.User;

public interface UserMapper {
	public void addUser(User user);
	public User findUser(String id);
	public void updateUser(User user);
	public User login(String username,String password);
	public List<User> findAllUser();
}
