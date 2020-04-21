package com.uzm.hylex.economy.controller;

import org.bukkit.Bukkit;

import com.uzm.hylex.economy.Core;

public class TopControllerTask implements Runnable {
	private int task;

	public TopControllerTask() {
		this.task = -1;
		reload();
	}

	public void run() {
		HylexPlayer.top();
	}

	public void reload() {
		if (this.task != -1) {
			Bukkit.getScheduler().cancelTask(this.task);
		}

		this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), this, 10L, 20 * 300);
	}
}
