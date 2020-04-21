package com.uzm.hylex.economy.command;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.uzm.hylex.economy.Core;
import com.uzm.hylex.economy.controller.HylexPlayer;
import com.uzm.hylex.economy.events.TranslationEvent;
import com.uzm.hylex.economy.events.TranslationEvent.Type;
import com.uzm.hylex.economy.utils.Methods;

public class MainCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		HylexPlayer player = null;
		if (sender instanceof Player) {
			player = HylexPlayer.get((Player) sender);
		}
		String[] blocked = { "set", "?", "help", "ajuda", "reset", "take", "add", "top", "rank", "create", "delete",
				"transfer" };

		if (Core.permissions.containsKey(label.toLowerCase())
				&& !sender.hasPermission(Core.permissions.get(label.toLowerCase()))) {
			sender.sendMessage("§b[Hylex] §cSem §c§npermissão §cpara executar esse comando.");
			return true;
		}
		if (label.equalsIgnoreCase("money") || label.equalsIgnoreCase("saldo") || label.equalsIgnoreCase("eco")
				|| label.equalsIgnoreCase("econ") || label.equalsIgnoreCase("dinheiro")) {

			switch (args.length) {
			case 0:
				if (player != null) {
					sender.sendMessage("§e* Seu saldo é de: §f" + Methods.modifyBalance(player.getBalance()) + "$");
				} else {
					sender.sendMessage("§c* Dica: Você deve ter uma conta registrada.");
				}
				break;
			case 1:
				switch (args[0].toLowerCase().replace("ajuda", "help").replace("?", "help")) {
				case "help":
					help((Player) sender, label);
					break;
				case "top":
					sender.sendMessage("");
					sender.sendMessage("                 §7Top §e§l10 §7jogadores");
					sender.sendMessage("               §7mais ricos do servidor!");

					sender.sendMessage("");

					String[] format = { "§c[♔] §a1º §a[Magnata] ", "§62º §7", "§c3º §7", "§74º §7", "§75º §7",
							"§76º §7", "§77º §7", "§78º §7", "§79º §7", "§710º §7" };
					for (int x = 0; x < 10; x++) {
						if (HylexPlayer.leaderboard.size() >= (x + 1)) {
							sender.sendMessage("  " + format[x] + HylexPlayer.leaderboard.get(x).split(":")[0]
									+ " §7- §7"
									+ Methods.modifyBalance(
											new BigDecimal(HylexPlayer.leaderboard.get(x).split(":")[1]).doubleValue())
									+ "§a$");
						} else {
							sender.sendMessage("  " + format[x] + "§kNínguemmmmm§r§7 - §k??§r§a$");
						}

					}
					sender.sendMessage("");
					sender.sendMessage("  §e* Obs: Ele é atualizado a cada 5 minutos.");

					sender.sendMessage("");

					break;
				default:

					if (HylexPlayer.register.get(args[0]) != null) {
						HylexPlayer target = HylexPlayer.get(HylexPlayer.register.get(args[0]));
						sender.sendMessage("§e* O saldo de §a" + target.getName() + " §eé: §f"
								+ Methods.modifyBalance(target.getBalance()) + "$");
					} else {
						sender.sendMessage("§cO jogador '" + args[0] + "' §cnão possui conta.");
					}

				}
				break;
			case 2:
				switch (args[0].toLowerCase()) {
				case "reset":
					if (HylexPlayer.register.get(args[1]) != null) {
						HylexPlayer target = HylexPlayer.get(HylexPlayer.register.get(args[1]));
						Bukkit.getServer().getPluginManager()
								.callEvent(new TranslationEvent(player, target, Type.RESETECON));
					} else {
						sender.sendMessage("§cO jogador '" + args[1] + "' §cnão possui conta.");
					}

					break;

				case "transfer":

					break;
				case "create":

					if ((sender.hasPermission("advancedeconomy." + args[0]))) {
						if (HylexPlayer.register.get(args[1]) == null
								&& !Lists.newArrayList(blocked).contains(args[1])) {
							sender.sendMessage("§e* Você criou uma conta com o nick §f'" + args[1] + "'§e.");
							HylexPlayer.create(Bukkit.getOfflinePlayer(args[1]));

						} else {
							sender.sendMessage("§cO jogador '" + args[1] + "' §cjá possui conta.");
						}
					}

					break;
				case "delete":

					if ((sender.hasPermission("advancedeconomy." + args[0]))) {
						if (HylexPlayer.register.get(args[1]) != null
								&& !Lists.newArrayList(blocked).contains(args[1])) {
							HylexPlayer.get(HylexPlayer.register.get(args[1])).delete();
							sender.sendMessage("§e* Você deletou a conta de §f'" + args[1] + "'§e.");

						} else {
							sender.sendMessage("§cO jogador '" + args[1] + "' §cnão possui conta.");
						}
					}

					break;
				default:
					help((Player) sender, label);
					break;
				}
				break;
			case 3:
				switch (args[0].toLowerCase()) {
				case "set":
					System.out.println("a");
					if (Methods.isNumber(args[2])) {
						if (HylexPlayer.register.get(args[1]) != null) {
							HylexPlayer target = HylexPlayer.get(HylexPlayer.register.get(args[1]));
							Bukkit.getServer().getPluginManager().callEvent(
									new TranslationEvent(player, target, Type.SETECON, Double.valueOf(args[2])));
						} else {
							sender.sendMessage("§cO jogador '" + args[1] + "' §cnão possui conta.");
						}
					} else {
						sender.sendMessage("§c* Dica: O valor deve ser um número inteiro ou decimal.");
					}

					break;
				case "take":
					if (Methods.isNumber(args[2])) {
						if (HylexPlayer.register.get(args[1]) != null) {
							HylexPlayer target = HylexPlayer.get(HylexPlayer.register.get(args[1]));
							Bukkit.getServer().getPluginManager().callEvent(
									new TranslationEvent(player, target, Type.TAKEECON, Double.valueOf(args[2])));
						} else {
							sender.sendMessage("§cO jogador '" + args[1] + "' §cnão possui conta.");
						}
					} else {
						sender.sendMessage("§c* Dica: O valor deve ser um número inteiro ou decimal.");
					}
					break;
				case "add":
					if (Methods.isNumber(args[2])) {
						if (HylexPlayer.register.get(args[1]) != null) {
							HylexPlayer target = HylexPlayer.get(HylexPlayer.register.get(args[1]));
							Bukkit.getServer().getPluginManager().callEvent(
									new TranslationEvent(player, target, Type.ADDECON, Double.valueOf(args[2])));
						} else {
							sender.sendMessage("§cO jogador '" + args[1] + "' §cnão possui conta.");
						}
					} else {
						sender.sendMessage("§c* Dica: O valor deve ser um número inteiro ou decimal.");
					}
					break;
				default:
					help((Player) sender, label);
					break;
				}
				break;
			default:
				help((Player) sender, label);
				break;
			}
		}
		return false;
	}

	public void help(Player player, String label) {
		player.sendMessage("");
		player.sendMessage("   §eAjuda do comando §f'" + label + "'");
		player.sendMessage("");
		player.sendMessage("  §e- §f/" + label + " <nick>  §7Veja o saldo de um jogador.");
		player.sendMessage("  §e- §f/" + label + " ?,help,ajuda  §7Veja as variações do comando " + label + ".");
		if (player.hasPermission("advancedeconomy.set"))
			player.sendMessage("  §e- §f/" + label + " set <nick> <valor> §7Defina o saldo de um jogador.");
		if (player.hasPermission("advancedeconomy.reset"))
			player.sendMessage("  §e- §f/" + label + " reset <nick> §7Resete o saldo de um jogador.");

		player.sendMessage("  §e- §f/" + label + " take <nick> <valor> §7Doe dinheiro para um jogador.");
		if (player.hasPermission("advancedeconomy.add"))
			player.sendMessage("  §e- §f/" + label + " add <nick> <valor> §7Adicione saldo na conta de um jogador.");
		player.sendMessage("  §e- §f/" + label + " top §7Veja os 10 jogadores mais ricos.");
		if (player.hasPermission("advancedeconomy.transfer"))
			player.sendMessage(
					"  §e- §f/" + label + " transfer <sql,mysql,config> §7Troque e transfira o tipo de armazenamento.");
		if (player.hasPermission("advancedeconomy.create"))
			player.sendMessage("  §e- §f/" + label + " create <nick> §7Crie um conta.");
		if (player.hasPermission("advancedeconomy.delete"))
			player.sendMessage("  §e- §f/" + label + " delete <nick> §7Delete um conta.");
		player.sendMessage("");
	}

	public static ArrayList<String> getInvoke() {
		return Lists.newArrayList("money", "saldo", "eco", "econ", "dinheiro");
	}

}
