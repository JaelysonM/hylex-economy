
package com.uzm.hylex.economy.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.uzm.hylex.economy.controller.HylexPlayer;

public class TranslationEvent extends Event {

	private boolean canceled;
	private Type type;
	private Object econ;
	private HylexPlayer target;
	private double value = 0;

	private static final HandlerList handlers = new HandlerList();

	public enum Type {
		TAKEECON, ADDECON, RESETECON, SETECON
	}

	public TranslationEvent(HylexPlayer econ, Type t, double value) {
		this.type = t;
		this.econ = econ;
		this.value = value;
	}

	public TranslationEvent(HylexPlayer econ, Type t) {
		this.type = t;
		this.econ = econ;

	}

	public TranslationEvent(HylexPlayer target, Type t, boolean console) {
		this.type = t;
		this.target = target;

	}

	public TranslationEvent(HylexPlayer econ, HylexPlayer target, Type t, double value) {
		this.type = t;
		this.econ = econ;
		this.target = target;
		this.value = value;

	}

	public TranslationEvent(HylexPlayer econ, HylexPlayer target, Type t) {
		this.type = t;
		this.econ = econ;
		this.target = target;
		this.econ = econ;

	}

	public Type getTranslationType() {
		return type;

	}

	public double getValue() {
		return value;

	}

	public HylexPlayer getEconPlayer() {
		if (econ instanceof HylexPlayer) {
			return (HylexPlayer) econ;
		} else {
			return null;
		}

	}

	public HylexPlayer getTargetPlayer() {
		return target;

	}

	public void setCancelled(boolean a) {
		this.canceled = a;
	}

	public void setTranslationType(Type a) {
		this.type = a;
	}

	public boolean isCancelled() {
		return this.canceled;

	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
