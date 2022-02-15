package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.gadgetStore.dao.BrandDAO;
import com.epam.gadgetStore.entity.Brand;

public class BrandDAOImpl extends AbstractBaseDAO<Brand> implements BrandDAO {
	private static final String GET_BRAND_BY_ID = "SELECT brand.id AS brand_id, brand.name AS brand_name  FROM brand WHERE id = ?";
	private static final String SELECT_ALL_BRANDS = "SELECT brand.id AS brand_id, brand.name AS brand_name FROM brand";

	@Override
	public Brand getById(Long id) {
		return getByParameters(GET_BRAND_BY_ID, id);
	}

	@Override
	public List<Brand> getAll() {
		return getAll(SELECT_ALL_BRANDS);
	}

	@Override
	Brand parseResultSet(ResultSet resultSet) throws SQLException {
		Brand brand = new Brand();
		brand.setId(resultSet.getLong("brand_id"));
		brand.setName(resultSet.getString("brand_name"));
		return brand;
	}
}
