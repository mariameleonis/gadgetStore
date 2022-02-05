package com.epam.gadgetStore.dao.factory;

import com.epam.gadgetStore.dao.BaseDAO;
import com.epam.gadgetStore.dao.impl.*;

public enum DAOFactory {
	LANGUAGE_DAO(new LanguageDAOImpl()),
	CATEGORY_DAO(new CategoryDAOImpl()),	
	BRAND_DAO(new BrandDAOImpl()),
	PRODUCT_DAO(new ProductDAOImpl()),	
	USER_DAO(new UserDAOImpl()),
	ORDER_DAO(new OrderDAOImpl()),
	ORDER_ITEM_DAO(new OrderItemDAOImpl());

	private BaseDAO baseDAO;
	
	 DAOFactory(BaseDAO DAOImpl) {
		baseDAO = DAOImpl;
	}
	
	public BaseDAO getDAO() {
		return baseDAO;
	}

}
