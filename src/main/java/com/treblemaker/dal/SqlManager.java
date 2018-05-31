package com.treblemaker.dal;

import com.treblemaker.dal.interfaces.ISqlManager;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class SqlManager implements ISqlManager {

	private static Connection connection = null;

	@Autowired
	private ConfigurationProvider configurationProvider;

	public Connection getConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		if(connection == null || connection.isClosed() || !connection.isValid(1000)){
			try {
				connection = DriverManager.getConnection(configurationProvider.getSpringDatasourceUrl(),configurationProvider.getSpringDatasourceUsername(), configurationProvider.getSpringDatasourcePassword());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return connection;
	}
}
