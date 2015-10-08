package edu.buffalo.ktb.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;

public class DBManager {
	Connection connection = null;
	private static DBManager dbManager = new DBManager();
	
	private DBManager() {
		
	}

	public static DBManager getInstance() throws SQLException{
		return dbManager;
	}
	
	public Connection getConnection() throws FileNotFoundException, IOException {
		
		final String URL = "jdbc:oracle:thin:@dbod-scan.acsu.buffalo.edu:1521/CSE601_2159.buffalo.edu";
		final String USERNAME = "sghodke";
		final String PASSWORD = "cse601";
		
		try {
			
			OracleDataSource ods = new OracleDataSource();
			ods.setUser(USERNAME);
			ods.setPassword(PASSWORD); 
			ods.setURL(URL);
			connection = ods.getConnection();
			
		} catch(Exception e) {
			System.err.println("ERROR: Failed to load JDBC driver.");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void releaseResources(Connection connection, Statement stmt, ResultSet resultSet) throws SQLException {
		
		if(resultSet != null) {
			resultSet.close();
		}
		if(stmt != null) {
			stmt.close();
		}
		if(connection != null) {
			connection.close();
		}
	}
	
	public void releaseResources(Connection connection) throws SQLException {
		releaseResources(connection, null, null);
	}

}
