package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.entity.Language;

public interface LanguageDAO extends BaseDAO {
	List<Language> getAll();
	Language findByName(String languageName);
}
