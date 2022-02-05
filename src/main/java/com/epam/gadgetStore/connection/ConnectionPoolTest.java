package com.epam.gadgetStore.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epam.gadgetStore.entity.Category;

public class ConnectionPoolTest {
	
	public static void main(String[] args) throws SQLException {
	
	ConnectionPool connectionPool = ConnectionPool.getInstance();
	Connection myConn = connectionPool.takeConnection();	
	
	String sql = "select * from category where lang_id = 2";
	
	Statement mySt = myConn.createStatement();
	ResultSet myRs = mySt.executeQuery(sql);
	
	
	
	while (myRs.next()) {
		System.out.println(myRs.getInt("id"));
		System.out.println(myRs.getString("name"));
		System.out.println(myRs.getString("item_title"));
	}
	
	connectionPool.returnConnection(myConn);
	
	try {
		if (myRs != null) {
			myRs.close();
		}

		if (mySt != null) {
			mySt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	}

}

