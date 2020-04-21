
package com.uzm.hylex.economy.controller;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uzm.hylex.economy.Core;

public class HylexPlayer {

	private String name;
	private String uuid;

	private BigDecimal balance;
	public static HashMap<String, String> register = Maps.newHashMap();
	public static ArrayList<Player> staff = Lists.newArrayList();
	public static HashMap<String, HylexPlayer> datas = new HashMap<String, HylexPlayer>();
	public static List<String> leaderboard = Lists.newArrayList();

	public HylexPlayer(String uuid, String name, BigDecimal balance) {
		this.name = name;
		;
		this.uuid = uuid;
		this.balance = balance;

		register.put(name, uuid);
	}

	public String getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance.doubleValue();
	}

	public void addBalance(double x) {
		this.balance = this.balance.add(BigDecimal.valueOf(x));
	}

	public void setBalance(double x) {
		this.balance = new BigDecimal(x);
	}

	public void setName(String name) {
		register.remove(name);
		register.put(name, uuid);
		this.name = name;
	}

	public void resetEcon() {
		this.balance = new BigDecimal(0.0D);
	}

	public void removeEcon(double x) {
		if (this.balance.subtract(BigDecimal.valueOf(x)).doubleValue() <= 0) {
			this.balance = new BigDecimal(0.0D);
		} else {
			this.balance = this.balance.subtract(BigDecimal.valueOf(x));
		}
	}

	public static HylexPlayer create(Player player) {
		if (datas.containsKey(player)) {
			return get(player);
		}
		datas.put(player.getUniqueId().toString(),
				new HylexPlayer(player.getUniqueId().toString(), player.getName(), new BigDecimal(0.0D)));
		datas.get(player.getUniqueId().toString()).saveAsync(0);
		return get(player);
	}

	public static HylexPlayer create(OfflinePlayer player) {
		if (datas.containsKey(player)) {
			return get(player);
		}
		datas.put(player.getUniqueId().toString(),
				new HylexPlayer(player.getUniqueId().toString(), player.getName(), new BigDecimal(0.0D)));
		datas.get(player.getUniqueId().toString()).saveAsync(0);

		return get(player);
	}

	public static void remove(Player p) {
		if (datas.containsKey(p)) {
			if (staff.contains(p)) {
				staff.remove(p);
			}
			datas.remove(p);
		}
	}

	public static HylexPlayer get(Player p) {
		if (!datas.containsKey(p.getUniqueId().toString())) {
			return null;
		} else {
			return (HylexPlayer) datas.get(p.getUniqueId().toString());
		}

	}

	public static HylexPlayer get(OfflinePlayer p) {
		if (!datas.containsKey(p.getUniqueId().toString())) {
			return null;
		} else {
			return (HylexPlayer) datas.get(p.getUniqueId().toString());
		}

	}

	public static HylexPlayer get(String uuid) {
		if (!datas.containsKey(uuid)) {
			return null;
		} else {
			return (HylexPlayer) datas.get(uuid);
		}

	}

	public static HylexPlayer byNick(String nick) {
		for (HylexPlayer e : getDatas()) {
			if (nick.equalsIgnoreCase(e.getName())) {
				return e;
			}
		}
		return null;
	}

	public static List<HylexPlayer> getDatas() {
		return new ArrayList<HylexPlayer>(datas.values());
	}

	private BukkitTask asyncSaveTask;

	public void saveAsync(long delay) {
		if (this.asyncSaveTask == null) {
			this.asyncSaveTask = Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), new Runnable() {
				public void run() {
					try {
						HylexPlayer.this.save();
						HylexPlayer.this.asyncSaveTask = null;
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}, delay);
		}
	}

	public void save() {
		try {
			Statement st = AccountsController.dataBase.getConnection().createStatement();
			ResultSet result = st.executeQuery("select balance from " + "economy" + " where name='" + this.name + "'");
			if (result.next()) {

				st.execute("update " + "economy" + " set balance='" + this.balance.toPlainString() + "' where uuid='"
						+ this.uuid + "'");
			} else {
				st.execute("insert into " + "economy" + " values ('" + this.uuid + "','" + this.name + "', '"
						+ this.balance.toPlainString() + "')");
			}
			st.close();
			result.close();

		} catch (Exception exception) {
			System.err
					.println("[Hylex - Economy] An error occurred while we was trying to load " + name + "'s account.");
		}
	}

	public void delete() {
		try {
			Statement st = AccountsController.dataBase.getConnection().createStatement();
			ResultSet result = st.executeQuery("select balance from " + "economy" + " where uuid='" + this.uuid + "'");
			if (result.next()) {
				st.execute("delete from economy where uuid='" + this.uuid + "'");
			}

			st.close();
			result.close();
		} catch (Exception exception) {
			System.err.println(
					"[Hylex - Economy] An error occurred while we was trying to delete " + name + "'s account.");
		}
		datas.remove(uuid);
		register.remove(name);
	}

	public static List<HylexPlayer> top() {
		try {
			leaderboard.clear();
			Statement st = AccountsController.getConnection().createStatement();

			ResultSet result = st
					.executeQuery("select * from economy order by cast(balance as decimal) desc limit 10;");
			while (result.next()) {
				String uuid = result.getString("name");
				String balance = result.getString("balance");
				leaderboard.add(uuid + ":" + balance);

			}
		} catch (Exception e) {
			System.err.println("[Hylex - Economy] An error occurred while we was trying to get top balances.");
		}
		return null;
	}
}
