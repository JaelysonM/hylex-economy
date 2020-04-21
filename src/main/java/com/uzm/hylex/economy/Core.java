package com.uzm.hylex.economy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uzm.hylex.economy.controller.AccountsController;
import com.uzm.hylex.economy.controller.AccountsController.Type;
import com.uzm.hylex.economy.hook.VaultHook;
import com.uzm.hylex.economy.controller.TopControllerTask;
import com.uzm.hylex.economy.java.util.Reflections;
import com.uzm.hylex.economy.storage.Database.ConnectionException;

public class Core extends JavaPlugin {

	PluginManager pm = Bukkit.getServer().getPluginManager();

	public static HashMap<String, String> permissions = Maps.newHashMap();

	public static Core main;

	private static String COREPATH;

	public static TopControllerTask task;

	public void onEnable() {
		main = this;
		COREPATH = getFile().getPath();

		setCommandPermissions();
		setupListeners();
		setupCommands();
		initHook();
		try {
			AccountsController.load(Type.SQLLITE);
		} catch (ConnectionException e) {
			System.err.println("[Hylex - Economy] An error occurred while trying to load the accounts.");
			e.printStackTrace();
		}

		task = new TopControllerTask();

		task.run();

	}

	public void onDisable() {

	}

	@SuppressWarnings("unchecked")
	public void setupCommands() {
		ArrayList<String> registred = Lists.newArrayList();

		List<Class<?>> classes = Reflections.getClasses("com.uzm.hylex.economy.command");
		try {
			for (Class<?> c : classes) {

				Method handshake = Reflections.getMethod(c, "getInvoke", new Class[0]);
				ArrayList<String> list = (ArrayList<String>) handshake.invoke(new ArrayList<String>());
				registred.add("");
				for (String r : list) {
					getCommand(r).setExecutor((CommandExecutor) c.newInstance());

				}
			}

		} catch (Exception e) {
			System.err.println("An error occurred while trying to register some commands.");
			e.printStackTrace();
		}

		Bukkit.getConsoleSender().sendMessage("§b[Hylex - Economy] §7We're registered §f(" + registred.size() + "/"
				+ classes.size() + ") §7commands.");

	}

	public void setupListeners() {
		ArrayList<String> registred = Lists.newArrayList();

		List<Class<?>> classes = Reflections.getClasses("com.uzm.hylex.economy.listeners");
		try {
			for (Class<?> c : classes) {
				pm.registerEvents((Listener) c.newInstance(), this);
				registred.add("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Bukkit.getConsoleSender().sendMessage("§b[Hylex - Economy] §7We're registered §f(" + registred.size() + "/"
				+ classes.size() + ") §7listeners.");
	}

	public void setCommandPermissions() {
		permissions.put("money", "");
		permissions.put("saldo", "");
		permissions.put("eco", "");
		permissions.put("econ", "");

		permissions.put("dinheiro", "");

	}

	public static Core getInstance() {

		return main;

	}

	public void initHook() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
			Bukkit.getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class,
					new VaultHook(), this, ServicePriority.Highest);
			getServer().getConsoleSender()
					.sendMessage("§f[AdvancedEconomy] §aVault found, plugin enabled successfully.");
		}
	}

	public static String getPath() {
		return COREPATH;
	}

}
