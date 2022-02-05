package com.epam.gadgetStore.shoppingcart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.epam.gadgetStore.entity.Product;

public class ShoppingCart {
	private Map<Product, Integer> items = new HashMap<>();

	public void addItem(Product product) {
		if (items.containsKey(product)) {
			Integer quantity = items.get(product) + 1;
			items.put(product, quantity);
		} else {
			items.put(product, 1);
		}
	}

	public void removeItem(Product product) {
		items.remove(product);
	}

	public int getTotalQuantity() {
		int totalQuantity = 0;

		Iterator<Product> iterator = items.keySet().iterator();

		while (iterator.hasNext()) {
			Product next = iterator.next();
			Integer quantity = items.get(next);
			totalQuantity += quantity;
		}

		return totalQuantity;
	}

	public double getTotalCost() {
		double totalCost = 0.0f;

		Iterator<Product> iterator = items.keySet().iterator();

		while (iterator.hasNext()) {
			Product product = iterator.next();
			Integer quantity = items.get(product);
			double subTotal = quantity * product.getPrice();
			totalCost += subTotal;
		}

		return totalCost;
	}
	
	public void updateCart(Map<Long, Integer> itemsToUpdate) {
		Iterator<Long> iterator = itemsToUpdate.keySet().iterator();
		
		while (iterator.hasNext()) {
			Product product = new Product(iterator.next());
			Integer quantity = itemsToUpdate.get(product.getId());
			items.put(product, quantity);
		}
		
	}
	
	public void clear() {
		items.clear();
	}
	
	public int getTotalItems() {
		return items.size();
	}

	public Map<Product, Integer> getItems() {
		return items;
	}
}
