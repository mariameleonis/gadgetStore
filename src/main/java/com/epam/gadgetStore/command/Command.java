package com.epam.gadgetStore.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException;
}
