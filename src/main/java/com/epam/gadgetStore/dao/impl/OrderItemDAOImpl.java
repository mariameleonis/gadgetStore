package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.gadgetStore.dao.OrderItemDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.OrderItem;
import com.epam.gadgetStore.entity.Product;

public class OrderItemDAOImpl extends AbstractBaseDAO<OrderItem> implements OrderItemDAO {
	private final ProductDAOImpl productDAO = (ProductDAOImpl) DAOFactory.PRODUCT_DAO.getDAO();
	
	private static final String ADD_ORDER_ITEM = "INSERT INTO order_item (order_id, product_id, quantity, selling_price) VALUES (?,?,?,?)";
	private static final String GET_ORDER_ITEMS_LOCALIZED_BY_ORDER = "SELECT oi.id, oi.order_id, oi.quantity, oi.selling_price, "
			+ "(oi.quantity*oi.selling_price) AS subtotal, pv.* FROM order_item oi JOIN product_view pv ON pv.product_id = oi.product_id "
			+ "WHERE oi.order_id = ? AND pv.language_id = ?";

	public List<OrderItem> getAllByOrderId(Long orderId, Long languageId) {
		return getAll(GET_ORDER_ITEMS_LOCALIZED_BY_ORDER, orderId, languageId);
	}
	
	public void add(OrderItem orderItem) {
		executeUpdate(ADD_ORDER_ITEM, orderItem.getOrderId(), orderItem.getProduct().getId(), orderItem.getQuantity(), orderItem.getSellingPrice());
	}

	@Override
	OrderItem parseResultSet(ResultSet resultSet) throws SQLException {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(resultSet.getLong("id"));
		orderItem.setOrderId(resultSet.getLong("order_id"));
		orderItem.setQuantity(resultSet.getInt("quantity"));
		orderItem.setSellingPrice(resultSet.getFloat("selling_price"));
		orderItem.setSubtotal(resultSet.getFloat("subtotal"));
		Product product = productDAO.parseResultSet(resultSet);
		orderItem.setProduct(product);
		return orderItem;
	}
}
