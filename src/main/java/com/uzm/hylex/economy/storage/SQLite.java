package com.uzm.hylex.economy.storage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import com.uzm.hylex.economy.Core;

public class SQLite implements Data {
	public File getFile() {
		return file;
	}

	public Connection getConnection() {
		return connection;
	}

	private File file = null;
	private Connection connection = null;

	public SQLite(File f) {
		file = f;
		if (!file.exists()) {
			file.getParentFile().mkdir();
			try {
				file.createNewFile();
			} catch (IOException localIOException) {
			}
		}
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + file);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.print("Falha ao pegar classe do SQLite");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("Falha ao criar conex�o no SQLite");
		}
	}

	public void queue(final PreparedStatement ps) {
		new BukkitRunnable() {
			public void run() {
				try {
					ps.execute();
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Core.getInstance());
	}
}
