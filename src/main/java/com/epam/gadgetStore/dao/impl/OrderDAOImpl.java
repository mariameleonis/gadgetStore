package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.User;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.*;

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
		order.setId(resultSet.getLong(ORDER_ID_COLUMN_LABEL));
		order.setTotal(resultSet.getFloat(ORDER_TOTAL_COLUMN_LABEL));
		order.setOrderDate(resultSet.getTimestamp(ORDER_DATE_COLUMN_LABEL));
		order.setShippingAddress(resultSet.getString(ORDER_ADDRESS_COLUMN_LABEL));
		order.setShippingPhone(resultSet.getString(ORDER_PHONE_COLUMN_LABEL));
		order.setStatus(resultSet.getString(ORDER_STATUS_COLUMN_LABEL));
		User user = new User();
		user.setId(resultSet.getLong(USER_ID_COLUMN_LABEL));
		user.setFirstName(resultSet.getString(USER_FIRSTNAME_COLUMN_LABEL));
		user.setLastName(resultSet.getString(USER_LASTNAME_COLUMN_LABEL));
		order.setUser(user);
		return order;
	}
}
