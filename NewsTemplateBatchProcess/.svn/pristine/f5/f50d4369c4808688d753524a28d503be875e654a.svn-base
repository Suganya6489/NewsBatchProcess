package com.scansee.batch.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scansee.batch.exception.NewsTemplateBatchProcessesException;

public class DBConnection {

	private static Logger LOG = LoggerFactory.getLogger(DBConnection.class);

	public static String DB_DRIVER;
	public static String DB_CONNECTION;
	public static String DB_USER;
	public static String DB_PASSWORD;

	static {
		try {
			DB_DRIVER = PropertiesReader.getPropertyValue("DB_DRIVER");
			DB_CONNECTION = PropertiesReader.getPropertyValue("DB_CONNECTION");
			DB_USER = PropertiesReader.getPropertyValue("DB_USER");
			DB_PASSWORD = PropertiesReader.getPropertyValue("DB_PASSWORD");
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException exception) {
			LOG.error("Inside DBConnection DRiver Registration: " + exception);
			try {
				throw new NewsTemplateBatchProcessesException(exception);
			} catch (NewsTemplateBatchProcessesException e) {
				LOG.info("Inside DBConnection :" + e.getMessage());
			}
		}

	}

	public static Connection getDBConnection() throws NewsTemplateBatchProcessesException {
		Connection dbConnection = null;
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			LOG.error("Inside DBConnection : Database Connection : " + e.getMessage());
		}
		return dbConnection;
	}

}
