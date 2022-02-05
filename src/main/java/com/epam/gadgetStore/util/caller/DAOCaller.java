package com.epam.gadgetStore.util.caller;

import java.sql.SQLException;

public class DAOCaller {

	public static <R> R tryCallGetByID(String idParameter, DAOFunction<R> function) throws SQLException {
		Long id = Long.parseLong(idParameter);
		if (id <= 0) {
			 throw new NumberFormatException("Id parameter is less or equals zero");
			 }
		if (function.apply(id) == null) {
			throw new IllegalArgumentException("Entity with id " + idParameter + " not exists");
		}
		return function.apply(id);
	}


	@FunctionalInterface
	public interface DAOFunction<R> {
		R apply(Long id) throws SQLException;
	}	
}
