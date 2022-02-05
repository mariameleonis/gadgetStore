package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.entity.User;

public interface UserDAO extends BaseDAO {
	User getById(Long id);		
	User findByEmail(String email);
	User findByPhone(String phone); 
	List<User> getAllCustomers();
	User checkLogin(String email, String password);
	void add(User user);
	void update(User user);	
	void updateStatus(User user);
}
