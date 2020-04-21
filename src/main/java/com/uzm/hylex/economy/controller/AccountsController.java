package com.uzm.hylex.economy.controller;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.uzm.hylex.economy.Core;
import com.uzm.hylex.economy.storage.Database;
import com.uzm.hylex.economy.storage.Database.ConnectionException;
import com.uzm.hylex.economy.storage.MySQL;
import com.uzm.hylex.economy.storage.SQLite;

public class AccountsController {
	public static Database dataBase;

	public enum Type {
		CONFIG, MYSQL, SQLLITE;
	}

	public static Database getDataBase() {
		return dataBase;
	}

	public static boolean isSevere() {
		return severe;
	}

	private static boolean severe = false;

	public static boolean load(Type type) throws ConnectionException {

		switch (type) {
		case MYSQL:

			dataBase = new Database(new MySQL(Core.getInstance().getConfig().getString("mysql.host"),
					Core.getInstance().getConfig().getString("mysql.user"),
					Core.getInstance().getConfig().getString("mysql.password"),
					Core.getInstance().getConfig().getString("mysql.database")));
			break;
		case SQLLITE:
			File f = new File(Core.getInstance().getDataFolder(), "dados.db");

			dataBase = new Database(new SQLite(f));
			break;
		default:
			break;
		}
		if (dataBase != null) {
			registerTables();

			try {
				PreparedStatement ps = dataBase.getConnection().prepareStatement("select * from economy");

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String uuid = rs.getString("uuid");
					BigDecimal balance = new BigDecimal(rs.getString("balance"));
					String name = rs.getString("name");
					HylexPlayer.datas.put(uuid, new HylexPlayer(uuid, name, balance));
				}
				rs.close();
				ps.close();
				Bukkit.getConsoleSender().sendMessage(
						"§b[Hylex - Economy] §7We're loaded §f(" + HylexPlayer.datas.size() + ") §7accounts.");
			} catch (SQLException e) {
				System.err.println("[Hylex - Economy] An error occurred while trying to load some accounts.");
				e.printStackTrace();
			}
		}
		return severe;
	}

	public static Connection getConnection() {
		return dataBase.getConnection();
	}

	public static boolean registerTables() {
		try {
			String query = "CREATE TABLE IF NOT EXISTS economy (uuid TEXT(64), name TEXT(32), balance text);";
			PreparedStatement ps = getConnection().prepareStatement(query);
			ps.execute();
			ps.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
