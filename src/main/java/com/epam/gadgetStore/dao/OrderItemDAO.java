package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.entity.OrderItem;

public interface OrderItemDAO extends BaseDAO {
	List<OrderItem> getAllByOrderId(Long orderId,  Long languageId);
	void add(OrderItem orderItem);
}
