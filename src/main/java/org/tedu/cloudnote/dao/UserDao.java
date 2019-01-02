package org.tedu.cloudnote.dao;

import org.tedu.cloudnote.entity.User;

public interface UserDao {
	public void save(User user);
	public User findByName(String name);	
	public Integer changePwd(User user);	
	
}
