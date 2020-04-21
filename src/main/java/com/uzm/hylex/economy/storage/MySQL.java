package com.uzm.hylex.economy.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class MySQL implements Data {
	private String url = null;
	private Properties info = null;
	private Connection connection = null;

	public MySQL(String host, String user, String pass, String database) {
		info = new Properties();
		info.put("autoReconnect", "true");
		info.put("user", user);
		info.put("password", pass);
		info.put("useUnicode", "true");
		info.put("characterEncoding", "utf8");

		url = ("jdbc:mysql://" + host + "/" + database);
	}

	public Connection getConnection() {
		try {
			if ((connection != null) && (!connection.isClosed()) && (connection.isValid(10))) {
				return connection;
			}
			connection = DriverManager.getConnection(url, info);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("Falha ao conectar no MySQL");
		}
		return null;
	}

	public void queue(PreparedStatement ps) {
		try {
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
