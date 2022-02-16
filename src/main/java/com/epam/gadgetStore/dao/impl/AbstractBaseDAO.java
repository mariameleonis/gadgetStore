package com.epam.gadgetStore.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.exception.DAOException;

import static com.epam.gadgetStore.util.caller.JDBCCaller.tryCallJDBC;

abstract class AbstractBaseDAO<E> {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	abstract E parseResultSet(ResultSet resultSet) throws SQLException;

	E executeStatementAndParseResultSet(PreparedStatement statement) throws SQLException {
		E entity;
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				entity = parseResultSet(resultSet);
			} else {
				entity = null;
			}
		}
		return entity;
	}

	List<E> executeStatementAndParseResultSetToList(PreparedStatement statement) throws SQLException {
		ArrayList<E> entities = new ArrayList<>();
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				entities.add(parseResultSet(resultSet));
			}
		}
		entities.trimToSize();
		return entities;
	}

	E getByParameters(String sqlQuery, Object... parameters) {
		try {
			return tryCallJDBC(sqlQuery, statement -> {
				populatePreparedStatement(statement, parameters);
				return executeStatementAndParseResultSet(statement);
			});
		} catch (DAOException e) {
			LOGGER.error("Failed to select row from database");
			throw new UnsupportedOperationException(e);
		}
	}

	List<E> getAll(String sqlQuery, Object... parameters) {
		try {
			return tryCallJDBC(sqlQuery, statement -> {
				populatePreparedStatement(statement, parameters);
				return executeStatementAndParseResultSetToList(statement);
			});
		} catch (DAOException e) {
			LOGGER.error("Failed to select rows from database");
			throw new UnsupportedOperationException(e);
		}
	}

	Long executeUpdateReturnKey(String sqlQuery, Object... parameters) {
		try {
			return tryCallJDBC(sqlQuery, statement -> {
				populatePreparedStatement(statement, parameters);
				statement.executeUpdate();
				try (ResultSet keys = statement.getGeneratedKeys()) {
					if (keys.next()) {
						return keys.getLong(1);
					} else {
						LOGGER.warn("Cannot return generated keys for specified sqlQuery parameter");
						throw new UnsupportedOperationException();
					}
				}
			});
		} catch (DAOException e) {
			LOGGER.error("Failed to update row to database");
			throw new UnsupportedOperationException(e);
		}
	}

	void executeUpdate(String sqlQuery, Object... parameters) {
		try {
			tryCallJDBC(sqlQuery, statement -> {
				populatePreparedStatement(statement, parameters);
				int result =  statement.executeUpdate();
			    LOGGER.info("Updated" + result + "rows");
			});
		} catch (DAOException e) {
			LOGGER.error("Failed to update row to database");
			throw new UnsupportedOperationException(e);
		}
	}

	private void populatePreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				try {
					statement.setObject(i + 1, parameters[i]);
				} catch (SQLException e) {
					LOGGER.error("Failed to populate prepare statement with specified values");
					throw new UnsupportedOperationException(e);
				}
			}
		}
	}
}
