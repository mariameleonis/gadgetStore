package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.gadgetStore.dao.LanguageDAO;
import com.epam.gadgetStore.entity.Language;

public class LanguageDAOImpl extends AbstractBaseDAO<Language> implements LanguageDAO {
	private static final String SELECT_ALL_LANGUAGES = "SELECT id as language_id, name as language_name FROM language";
	private static final String GET_LANGUAGE_BY_NAME = "SELECT id as language_id, name as language_name FROM language WHERE name = ?";

	@Override
	Language parseResultSet(ResultSet resultSet) throws SQLException {
		Language language = new Language();
		language.setId(resultSet.getLong("language_id"));
		language.setName(resultSet.getString("language_name"));
		return language;
	}

	public List<Language> getAll() {
		return getAll(SELECT_ALL_LANGUAGES);
	}

	public Language findByName(String languageName) {
		return getByParameters(GET_LANGUAGE_BY_NAME, languageName);
	}
}
