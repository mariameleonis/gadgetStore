package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.entity.User;

public class UserDAOImpl extends AbstractBaseDAO<User> implements UserDAO {
	private static final String ADD_USER = "INSERT INTO USER (firstname, lastname, email, password, isAdmin, phoneNumber) VALUES (?,?,?,?,?,?)";
	private static final String GET_ALL_CUSTOMERS = "SELECT * FROM user WHERE isAdmin = false";
	private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE ID = ?";
	private static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
	private static final String GET_USER_BY_PHONE = "SELECT * FROM user WHERE phoneNumber = ?";
	private static final String CHECK_LOGIN = "SELECT * FROM user WHERE email = ? AND password = ?";
	private static final String UPDATE_USER = "UPDATE user SET firstname = ?, lastname = ?, email = ?, password = ?, isAdmin = ?, phoneNumber = ? WHERE id = ?";
	private static final String UPDATE_USER_STATUS = "UPDATE user SET isBlocked = ? where id = ?";

	public User getById(Long id) {
		return getByParameters(GET_USER_BY_ID, id);
	}
	
	public User findByEmail(String email) {
		return getByParameters(GET_USER_BY_EMAIL, email);
	}
	
	public User findByPhone(String phone) {
		return getByParameters(GET_USER_BY_PHONE, phone);
	}
	
	public List<User> getAllCustomers() {
		return getAll(GET_ALL_CUSTOMERS);
	}
	
	public User checkLogin(String email, String password) {
		return getByParameters(CHECK_LOGIN, email, password);
	}

	public void add(User user) {
		executeUpdate(ADD_USER, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
				user.getIsAdmin(), user.getPhoneNumber());
	}

	public void update(User user) {
		executeUpdate(UPDATE_USER, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
				user.getIsAdmin(), user.getPhoneNumber(), user.getId());
	}

	public void updateStatus(User user) {
		executeUpdate(UPDATE_USER_STATUS, !user.getIsBlocked(), user.getId());
	}

	@Override
	User parseResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong(1));
		user.setFirstName(resultSet.getString(2));
		user.setLastName(resultSet.getString(3));
		user.setEmail(resultSet.getString(4));
		user.setPassword(resultSet.getString(5));
		user.setIsAdmin(resultSet.getBoolean(6));
		user.setPhoneNumber(resultSet.getString(7));
		user.setIsBlocked(resultSet.getBoolean(8));
		return user;
	}	
}
