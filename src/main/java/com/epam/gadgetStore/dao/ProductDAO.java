package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.entity.Product;

public interface ProductDAO extends BaseDAO {
	Product getById(Long productId, Long languageId);
	List<Product> getAllByLangId(Long languageId);
	List<Product> getNewProducts(Long languageId);
	List<Product> getBestsellingProducts(Long languageId);
	List<Product> searchProducts(String keyword, Long languageId);
	Product findByName (String productName);
	List<Product> findByCategory(Long categoryId, Long language_id);
	Long add(Product product);
	void update(Product product);
	void delete(Long id);	
	List<Product> findByLeafCategories(List<Long> leafCategories, Long languageId);
}
