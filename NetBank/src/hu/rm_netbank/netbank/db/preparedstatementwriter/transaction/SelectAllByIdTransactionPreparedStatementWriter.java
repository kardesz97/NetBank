package hu.rm_netbank.netbank.db.preparedstatementwriter.transaction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectAllByIdTransactionPreparedStatementWriter implements Preparedstatementwriter<Transaction> {

	private long transactionId;

	public SelectAllByIdTransactionPreparedStatementWriter(long transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setLong(1, transactionId);
	}

}