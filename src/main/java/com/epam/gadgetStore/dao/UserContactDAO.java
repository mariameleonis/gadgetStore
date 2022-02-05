package com.epam.gadgetStore.dao;

import java.sql.SQLException;
import java.util.List;

import com.epam.gadgetStore.entity.UserContact;

public interface UserContactDAO extends BaseDAO {
	
	UserContact getById(Long id) throws SQLException;
	List<UserContact> getAll() throws SQLException;
	void add(UserContact userContact) throws SQLException;
	void update(UserContact userContact) throws SQLException;
	void delete(Long id) throws SQLException;

}
