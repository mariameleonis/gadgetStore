package com.epam.gadgetStore.util.caller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.epam.gadgetStore.connection.ConnectionPool;
import com.epam.gadgetStore.exception.DAOException;


public class JDBCCaller {

	public static <R> R tryCallJDBC(String sqlQuery, DAOFunction<R> function) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.takeConnection();
		try (PreparedStatement statement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
			return function.apply(statement);
		} catch (SQLException e) {
	            throw new DAOException(e);        
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	public static void tryCallJDBC(String sqlQuery, DAOVoidFunction function) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.takeConnection();
		try (PreparedStatement statement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
			function.apply(statement);
		} catch (SQLException e) {
            throw new DAOException(e);   
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@FunctionalInterface
	public interface DAOFunction<R> {
		R apply(PreparedStatement statement) throws SQLException;
	}

	@FunctionalInterface
	public interface DAOVoidFunction {
		void apply(PreparedStatement statement) throws SQLException;
	}
}
