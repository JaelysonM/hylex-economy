package com.uzm.hylex.economy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.uzm.hylex.economy.events.TranslationEvent;
import com.uzm.hylex.economy.events.TranslationEvent.Type;
import com.uzm.hylex.economy.utils.Methods;

public class EconomyListeners implements Listener {

	@EventHandler
	void translation(TranslationEvent e) {
		if (e.isCancelled()) {
			return;
		}

		if (e.getEconPlayer() == null) {
			if (e.getTranslationType() == Type.RESETECON) {
				e.getTargetPlayer().resetEcon();

				e.getTargetPlayer().saveAsync(20L);
				if (Bukkit.getPlayerExact(e.getTargetPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getTargetPlayer().getName())
							.sendMessage("§c* " + "CONSOLE" + " §cresetou sua conta.");
				}

			}

			if (e.getTranslationType() == Type.ADDECON) {
				e.getTargetPlayer().addBalance(e.getValue());
				e.getTargetPlayer().saveAsync(20L);
				if (Bukkit.getPlayerExact(e.getTargetPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getTargetPlayer().getName()).sendMessage("§e* Foi adicionado "
							+ Methods.modifyBalance(e.getValue()) + "$ §ena sua conta por: " + "CONSOLE" + "§e.");
				}
			}
			if (e.getTranslationType() == Type.SETECON) {
				e.getTargetPlayer().setBalance(e.getValue());
				e.getTargetPlayer().saveAsync(20L);

				if (Bukkit.getPlayerExact(e.getTargetPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getTargetPlayer().getName()).sendMessage("§e* Seu saldo foi setado para §f"
							+ Methods.modifyBalance(e.getValue()) + "$ §epor: " + "CONSOLE" + "§e.");
				}

			}
			return;
		}
		if (e.getTargetPlayer() != null) {
			if (e.getTranslationType() == Type.RESETECON) {
				e.getTargetPlayer().resetEcon();
				e.getTargetPlayer().saveAsync(20L);
				if (Bukkit.getPlayerExact(e.getEconPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getEconPlayer().getName())
							.sendMessage("§e* Você resetou a conta de §f'" + e.getTargetPlayer().getName() + "'§e.");
				}

				if (Bukkit.getPlayerExact(e.getTargetPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getTargetPlayer().getName())
							.sendMessage("§c* " + e.getEconPlayer().getName() + " §cresetou sua conta.");
				}

			}

			if (e.getTranslationType() == Type.ADDECON) {
				e.getTargetPlayer().addBalance(e.getValue());
				e.getTargetPlayer().saveAsync(20L);
				if (Bukkit.getPlayerExact(e.getEconPlayer().getName()) != null)
					Bukkit.getPlayerExact(e.getEconPlayer().getName())
							.sendMessage("§e* Foi adicionado §f" + Methods.modifyBalance(e.getValue())
									+ "$ §ena conta de §f'" + e.getTargetPlayer().getName() + "'§e.");

				if (Bukkit.getPlayer(e.getEconPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getEconPlayer().getName())
							.sendMessage("§e* Foi adicionado " + Methods.modifyBalance(e.getValue())
									+ "$ §ena sua conta por: " + e.getEconPlayer().getName() + "§e.");
				}
			}
			if (e.getTranslationType() == Type.TAKEECON) {
				if (e.getEconPlayer() == e.getTargetPlayer()) {
					if (Bukkit.getPlayerExact(e.getEconPlayer().getName()) != null) {
						Bukkit.getPlayerExact(e.getEconPlayer().getName())
								.sendMessage("§c* Você não pode doar para si mesmo.");
					}
					e.setCancelled(true);
					return;
				}
				if (e.getEconPlayer().getBalance() >= e.getValue()) {
					e.getTargetPlayer().addBalance(e.getValue());
					e.getTargetPlayer().saveAsync(20L);
					e.getEconPlayer().removeEcon(e.getValue());
					e.getEconPlayer().saveAsync(25L);
					if (Bukkit.getPlayerExact(e.getEconPlayer().getName()) != null)
						Bukkit.getPlayerExact(e.getEconPlayer().getName())
								.sendMessage("§e* Você doou §f" + Methods.modifyBalance(e.getValue()) + "$ §epara §f'"
										+ e.getTargetPlayer().getName() + "'§e.");

					if (Bukkit.getPlayerExact(e.getTargetPlayer().getName()) != null) {
						Bukkit.getPlayerExact(e.getTargetPlayer().getName())
								.sendMessage("§e* Você recebeu " + Methods.modifyBalance(e.getValue()) + "$ §ede: "
										+ e.getEconPlayer().getName() + "§e.");
					}
				} else {
					e.setCancelled(true);

					Bukkit.getPlayerExact(e.getEconPlayer().getName()).sendMessage("§c* Você não tem esse saldo.");

				}

			}
			if (e.getTranslationType() == Type.SETECON) {
				e.getTargetPlayer().setBalance(e.getValue());
				e.getTargetPlayer().saveAsync(20L);
				if (Bukkit.getPlayerExact(e.getEconPlayer().getName()) != null)
					Bukkit.getPlayerExact(e.getEconPlayer().getName())
							.sendMessage("§e* Saldo de '" + e.getTargetPlayer().getName() + "' alterado para: §f"
									+ Methods.modifyBalance(e.getValue()) + "$");

				if (Bukkit.getPlayerExact(e.getTargetPlayer().getName()) != null) {
					Bukkit.getPlayerExact(e.getTargetPlayer().getName()).sendMessage("§e* Seu saldo foi setado para §f"
							+ Methods.modifyBalance(e.getValue()) + "$ §epor: " + e.getEconPlayer().getName() + "§e.");
				}

			}
		}

	}
}