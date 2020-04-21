package com.uzm.hylex.economy.hook;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.uzm.hylex.economy.controller.HylexPlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class VaultHook implements Economy {

	@Override
	public EconomyResponse bankBalance(String arg0) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String arg0) {
		if (HylexPlayer.byNick(arg0) == null) {
			HylexPlayer.create(Bukkit.getOfflinePlayer(arg0));
			return true;
		}
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0) {

		if (HylexPlayer.datas.get(arg0.getUniqueId().toString()) == null) {
			HylexPlayer.create(arg0);
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String arg0, String arg1) {

		if (HylexPlayer.byNick(arg0) == null) {
			HylexPlayer.create(Bukkit.getOfflinePlayer(arg0));
		}
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {

		if (HylexPlayer.datas.get(arg0.getUniqueId().toString()) == null) {
			HylexPlayer.create(arg0);
			return true;
		}
		return false;
	}

	@Override
	public String currencyNamePlural() {

		return "$";
	}

	@Override
	public String currencyNameSingular() {

		return "$";
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		HylexPlayer player = HylexPlayer.byNick(arg0);
		if (player != null) {
			player.delete();
			HylexPlayer.datas.remove(player.getUUID());
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}

	}

	@Override
	public EconomyResponse depositPlayer(String arg0, double arg1) {
		HylexPlayer player = HylexPlayer.byNick(arg0);

		if (player != null) {
			player.addBalance(arg1);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer arg0, double arg1) {
		HylexPlayer player = HylexPlayer.get(arg0);
		if (player != null) {
			player.addBalance(arg1);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	@Override
	public EconomyResponse depositPlayer(String arg0, String arg1, double arg2) {
		HylexPlayer player = HylexPlayer.byNick(arg0);
		if (player != null) {
			player.addBalance(arg2);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer arg0, String arg1, double arg2) {
		HylexPlayer player = HylexPlayer.get(arg0);
		if (player != null) {
			player.addBalance(arg2);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	public static NumberFormat numberFormat;

	@Override
	public String format(double value) {
		numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("en-US"));
		if (value <= 1.0D) {
			if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.").length > 1) {
				if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.")[1].length() == 3) {
					return numberFormat.format(value).replace(" ", "").substring(0,
							numberFormat.format(value).replace(" ", "").length() - 1);
				}
			} else {
				return numberFormat.format(value).concat(" ").concat("").replace(" ", "");
			}

		} else if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.").length > 1) {
			if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.")[1].length() == 3) {
				return numberFormat.format(value).replace(" ", "").substring(0,
						numberFormat.format(value).replace(" ", "").length() - 1);
			}
		} else {
			return numberFormat.format(value).concat(" ").concat("").replace(" ", "");
		}
		return numberFormat.format(value).concat(" ").concat("").replace(" ", "");
	}

	@Override
	public int fractionalDigits() {

		return -1;
	}

	@Override
	public double getBalance(String arg0) {
		HylexPlayer player = HylexPlayer.byNick(arg0);

		if (player != null) {

			return player.getBalance();
		} else {
			return 0;
		}
	}

	@Override
	public double getBalance(OfflinePlayer arg0) {
		HylexPlayer player = HylexPlayer.get(arg0);

		if (player != null) {

			return player.getBalance();
		} else {
			return 0;
		}
	}

	@Override
	public double getBalance(String arg0, String arg1) {

		HylexPlayer player = HylexPlayer.byNick(arg0);

		if (player != null) {

			return player.getBalance();
		} else {
			return 0;
		}
	}

	@Override
	public double getBalance(OfflinePlayer arg0, String arg1) {

		HylexPlayer player = HylexPlayer.get(arg0);

		if (player != null) {

			return player.getBalance();
		} else {
			return 0;
		}
	}

	@Override
	public List<String> getBanks() {

		return new ArrayList<String>();
	}

	@Override
	public String getName() {

		return "AdvancedEconomy";
	}

	@Override
	public boolean has(String arg0, double arg1) {
		HylexPlayer player = HylexPlayer.byNick(arg0);
		if (player != null) {

			return player.getBalance() >= arg1;
		} else {
			return false;
		}
	}

	@Override
	public boolean has(OfflinePlayer arg0, double arg1) {
		HylexPlayer player = HylexPlayer.get(arg0);
		if (player != null) {

			return player.getBalance() >= arg1;
		} else {
			return false;
		}
	}

	@Override
	public boolean has(String arg0, String arg1, double arg2) {

		HylexPlayer player = HylexPlayer.byNick(arg0);
		if (player != null) {

			return player.getBalance() >= arg2;
		} else {
			return false;
		}
	}

	@Override
	public boolean has(OfflinePlayer arg0, String arg1, double arg2) {

		HylexPlayer player = HylexPlayer.get(arg0);
		if (player != null) {

			return player.getBalance() >= arg2;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasAccount(String arg0) {

		return HylexPlayer.byNick(arg0) != null;
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0) {

		return HylexPlayer.get(arg0) != null;
	}

	@Override
	public boolean hasAccount(String arg0, String arg1) {

		return HylexPlayer.byNick(arg0) != null;
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0, String arg1) {

		return HylexPlayer.get(arg0) != null;
	}

	@Override
	public boolean hasBankSupport() {

		return false;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {

		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "§cNão é possivel fazer isso");
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public EconomyResponse withdrawPlayer(String arg0, double arg1) {

		HylexPlayer player = HylexPlayer.byNick(arg0);
		if (player != null) {
			player.removeEcon(arg1);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer arg0, double arg1) {

		HylexPlayer player = HylexPlayer.get(arg0);
		if (player != null) {
			player.removeEcon(arg1);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	@Override
	public EconomyResponse withdrawPlayer(String arg0, String arg1, double arg2) {

		HylexPlayer player = HylexPlayer.byNick(arg0);
		if (player != null) {
			player.removeEcon(arg2);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer arg0, String arg1, double arg2) {

		HylexPlayer player = HylexPlayer.get(arg0);
		if (player != null) {
			player.removeEcon(arg2);
			player.saveAsync(20L);
			return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "§cConta não registrada");
		}
	}

}
