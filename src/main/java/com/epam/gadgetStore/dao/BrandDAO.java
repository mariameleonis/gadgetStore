package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.entity.Brand;

public interface BrandDAO extends BaseDAO {
	Brand getById(Long id);
	List<Brand> getAll();
}
