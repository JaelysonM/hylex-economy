package com.uzm.hylex.economy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.common.collect.Lists;
import com.uzm.hylex.economy.controller.HylexPlayer;

public class PlayerListeners implements Listener {

	public String[] antidup = { "set", "?", "help", "ajuda", "take", "add", "top", "rank", "create", "delete",
			"transfer" };

	@EventHandler
	private void rank(PlayerJoinEvent e) {

		if (Lists.newArrayList(antidup).contains(e.getPlayer().getName())) {

			e.getPlayer().kickPlayer(
					"§c§lHYLEX - Alert \n\n§cYou are using a blacklisted nickname!\n\nFor more informations: §fContact a staff.");
		}

		if (HylexPlayer.get(e.getPlayer()) == null) {
			HylexPlayer.create(e.getPlayer());
			HylexPlayer.get(e.getPlayer()).save();

		} else {
			if (!HylexPlayer.get(e.getPlayer()).getName().equals(e.getPlayer().getName())) {
				HylexPlayer.get(e.getPlayer()).setName(e.getPlayer().getName());
				HylexPlayer.get(e.getPlayer()).saveAsync(20L);
			}
		}
	}
}
