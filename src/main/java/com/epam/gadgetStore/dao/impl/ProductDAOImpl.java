package com.epam.gadgetStore.dao.impl;

import static com.epam.gadgetStore.util.caller.JDBCCaller.tryCallJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.epam.gadgetStore.dao.ProductDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Product;

public class ProductDAOImpl extends AbstractBaseDAO<Product> implements ProductDAO {
	private static final String ADD_PRODUCT = "INSERT INTO PRODUCT (category_id, brand_id, name, description, price, image) "
			+ "VALUES (?,?,?,?,?,?)";
	private static final String GET_PRODUCT_BY_ID = "SELECT * FROM product_view WHERE product_id = ? AND language_id = ?";
	private static final String GET_ALL_PRODUCTS_BY_LANGUAGE_ID = "SELECT * FROM product_view WHERE language_id = ? ORDER BY createdAt DESC";
	private static final String UPDATE_PRODUCT = "UPDATE PRODUCT SET category_id = ?, brand_id = ?, name = ?, description = ?, "
			+ "price = ?, image = ? WHERE id = ?";
	private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";
	private static final String GET_PRODUCT_BY_NAME = "SELECT * FROM PRODUCT WHERE name = ?";
	private static final String GET_PRODUCT_LIST_BY_CATEGORY = "SELECT * FROM product_view WHERE category_id = ? AND language_id=?";
	private static final String GET_NEW_PRODUCTS = "SELECT * FROM product_view WHERE language_id=? ORDER BY createdAt DESC LIMIT 4";
	private static final String GET_BESTSELLING_PRODUCTS = "SELECT pv.* FROM order_item oi "
			+ "JOIN product_view pv ON pv.product_id = oi.product_id AND language_id = ? "
			+ "GROUP BY oi.product_id ORDER BY sum(quantity) DESC LIMIT 4";
	private static final String SEARCH_PRODUCTS = "SELECT * FROM product_view WHERE match(name,description) AGAINST (? IN NATURAL LANGUAGE MODE) "
			+ "AND language_id = ?";

	private final CategoryDAOImpl categoryDAO = (CategoryDAOImpl) DAOFactory.CATEGORY_DAO.getDAO();
	private final BrandDAOImpl brandDAO = (BrandDAOImpl) DAOFactory.BRAND_DAO.getDAO();

	public Product getById(Long productId, Long languageId) {
		return getByParameters(GET_PRODUCT_BY_ID, productId, languageId);
	}

	public List<Product> getAllByLangId(Long languageId) {	
		return getAll(GET_ALL_PRODUCTS_BY_LANGUAGE_ID, languageId);
	}
	
	public List<Product> getNewProducts(Long languageId) {
		return getAll(GET_NEW_PRODUCTS, languageId);
	}
	
	public List<Product> getBestsellingProducts(Long languageId) {
		return getAll(GET_BESTSELLING_PRODUCTS, languageId);
	}
	
	public List<Product> searchProducts(String keyword, Long languageId) {
		return getAll(SEARCH_PRODUCTS, keyword, languageId);
	}
	
	public Product findByName(String productName) {
		return getByParameters(GET_PRODUCT_BY_NAME, productName);
	}
	
	public List<Product> findByCategory(Long categoryId, Long languageId) {
		return getAll(GET_PRODUCT_LIST_BY_CATEGORY, categoryId, languageId);
	}
	
	public Long add(Product product) {
		return executeUpdateReturnKey(ADD_PRODUCT, product.getCategory().getId(), product.getBrand().getId(), product.getName(), 
				product.getDescription(), product.getPrice(), product.getImage());
	}

	public void update(Product product) {
		executeUpdate(UPDATE_PRODUCT, product.getCategory().getId(), product.getBrand().getId(), product.getName(), 
				product.getDescription(), product.getPrice(), product.getImage(), product.getId());
	}

	public void delete(Long id) {
		executeUpdate(DELETE_PRODUCT, id);
	}
	
	public List<Product> findByLeafCategories(List<Long> leafCategories, Long languageId) {
		String placeHolders = String.join(",", Collections.nCopies(leafCategories.size(), "?"));
		String sql = "SELECT * FROM product_view WHERE language_id=? AND category_id in (" + placeHolders + ")";
		return tryCallJDBC(sql, statement -> {
			statement.setLong(1, languageId);
			int i = 2;
		    for (Long categoryId : leafCategories) {
		        statement.setLong(i++, categoryId);
		    }
			return executeStatementAndParseResultSetToList(statement);
		});
	}

	@Override
	Product parseResultSet(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setId(resultSet.getLong("product_id"));
		product.setName(resultSet.getString("name"));
		product.setDescription(resultSet.getString("description"));
		product.setPrice(resultSet.getFloat("price"));
		product.setImage(resultSet.getBytes("image"));
		product.setCategory(categoryDAO.parseResultSet(resultSet));
		product.setBrand(brandDAO.parseResultSet(resultSet));
		return product;
	}
}
