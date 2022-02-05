package com.epam.gadgetStore.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.epam.gadgetStore.util.caller.JDBCCaller.tryCallJDBC;

abstract class AbstractBaseDAO<E> {

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

	E getByParameters(String SQLQuery, Object... parameters) {
		return tryCallJDBC(SQLQuery, statement -> {
			populatePreparedStatement(statement, parameters);
			return executeStatementAndParseResultSet(statement);
		});
	}

	List<E> getAll(String SQLQuery, Object... parameters) {
		return tryCallJDBC(SQLQuery, statement -> {
			populatePreparedStatement(statement, parameters);
			return executeStatementAndParseResultSetToList(statement);
		});
	}

	Long executeUpdateReturnKey(String SQLQuery, Object... parameters) {
		return tryCallJDBC(SQLQuery, statement -> {
			populatePreparedStatement(statement, parameters);
			statement.executeUpdate();
			try (ResultSet keys = statement.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getLong(1);
				} else {
					throw new UnsupportedOperationException(
							"Cannot return generated keys for specified SQLQuery parameter");
				}
			}
		});
	}

	void executeUpdate(String SQLQuery, Object... parameters) {
		tryCallJDBC(SQLQuery, statement -> {
			populatePreparedStatement(statement, parameters);
			statement.executeUpdate();
		});
	}

	private void populatePreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				try {
					statement.setObject(i + 1, parameters[i]);
				} catch (SQLException e) {
					throw new UnsupportedOperationException(e);
				}
			}
		}
	}
}
