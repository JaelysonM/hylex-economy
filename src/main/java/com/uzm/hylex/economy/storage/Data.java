package com.uzm.hylex.economy.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract interface Data {
	public abstract Connection getConnection();

	public abstract void queue(PreparedStatement paramPreparedStatement);
}
