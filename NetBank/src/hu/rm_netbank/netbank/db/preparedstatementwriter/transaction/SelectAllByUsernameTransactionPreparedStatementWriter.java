package hu.rm_netbank.netbank.db.preparedstatementwriter.transaction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectAllByUsernameTransactionPreparedStatementWriter implements Preparedstatementwriter<Transaction> {

	private String username;

	public SelectAllByUsernameTransactionPreparedStatementWriter(String username) {
		this.username = username;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, username);
	}

}