package com.uzm.hylex.economy.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	public Data getDb() {
		return db;
	}

	private Data db = null;

	public Database(Data db) throws ConnectionException {
		try {
			try {
				if (!db.getConnection().isValid(10)) {
					throw new ConnectionException("Banco de dados invalido");
				}
			} catch (AbstractMethodError localAbstractMethodError) {
			}
			this.db = db;
		} catch (SQLException e) {
			throw new ConnectionException(e.getMessage());
		}
	}

	public Connection getConnection() {
		return db.getConnection();
	}

	public void execute(String query, Object... objects) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			db.queue(ps);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void execute(String query, boolean flag, Object... objects) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object getValue(String into, String uuid) {
		Object value = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT " + into + " FROM info WHERE uuid = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return value;
			}
			value = rs.getObject(into);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static class ConnectionException extends Exception {
		private static final long serialVersionUID = 8348749992936357317L;

		public ConnectionException(String msg) {
			super();
		}
	}
}
