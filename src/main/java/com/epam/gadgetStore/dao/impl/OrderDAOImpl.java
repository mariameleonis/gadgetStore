package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.User;

public class OrderDAOImpl extends AbstractBaseDAO<Order> implements OrderDAO {

	private static final String ADD_ORDER = "INSERT INTO `order` (user_id, total, shipping_address, shipping_phone) VALUES (?,?,?,?)";
	private static final String UPDATE_ORDER = "UPDATE `order` SET shipping_address = ?, shipping_phone = ?, status = ? WHERE id = ?";
	private static final String GET_ORDER_BY_ID = "SELECT o.*, u.firstname, u.lastname FROM `order` o "
			+ "JOIN user u ON u.id = o.user_id WHERE o.id = ?";
	private static final String GET_ORDER_WITH_CUSTOMER_ID = "SELECT o.*, u.firstname, u.lastname FROM `order` o "
			+ "JOIN user u ON u.id = o.user_id WHERE o.id = ? AND o.user_id = ?";
	private static final String GET_ALL_ORDERS = "SELECT o.*, u.firstname, u.lastname FROM `order` o "
			+ "JOIN user u ON u.id = o.user_id";
	private static final String GET_ALL_ORDERS_BY_USER_ID = "SELECT o.*, u.firstname, u.lastname FROM `order` o "
			+ "JOIN user u ON u.id = o.user_id WHERE o.user_id = ?";
	
	public Order getByIdAndCustomerId(Long id, Long customerId) {
		return getByParameters(GET_ORDER_WITH_CUSTOMER_ID, id, customerId);
	}
	
	public Order getById(Long id) {
		return getByParameters(GET_ORDER_BY_ID, id);
	}

	public List<Order> getAll() {
		return getAll(GET_ALL_ORDERS);
	}

	public List<Order> getAllByCustomer(Long userId) {
		return getAll(GET_ALL_ORDERS_BY_USER_ID, userId);
	}

	public Long add(Order order) {
		return executeUpdateReturnKey(ADD_ORDER, order.getUser().getId(), order.getTotal(), order.getShippingAddress(), order.getShippingPhone());
	}

	public void update(Order order) {
		executeUpdate(UPDATE_ORDER, order.getShippingAddress(), order.getShippingPhone(), order.getStatus(), order.getId());
	}
	
	@Override
	Order parseResultSet(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getLong("id"));
		order.setTotal(resultSet.getFloat("total"));
		order.setOrderDate(resultSet.getTimestamp("placedAt"));
		order.setShippingAddress(resultSet.getString("shipping_address"));
		order.setShippingPhone(resultSet.getString("shipping_phone"));
		order.setStatus(resultSet.getString("status"));
		User user = new User();
		user.setId(resultSet.getLong("user_id"));
		user.setFirstName(resultSet.getString("firstname"));
		user.setLastName(resultSet.getString("lastname"));
		order.setUser(user);
		return order;
	}
}
