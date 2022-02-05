package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.entity.Order;

public interface OrderDAO extends BaseDAO {
	Order getByIdAndCustomerId(Long id, Long customerId);
	Order getById(Long Id);
	List<Order> getAll();
	List<Order> getAllByCustomer(Long userId);
	Long add(Order order);
	void update(Order order);
}
