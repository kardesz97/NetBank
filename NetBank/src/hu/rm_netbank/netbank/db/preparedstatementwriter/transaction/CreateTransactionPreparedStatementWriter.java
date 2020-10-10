package hu.rm_netbank.netbank.db.preparedstatementwriter.transaction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class CreateTransactionPreparedStatementWriter implements Preparedstatementwriter<Transaction> {

	private Transaction transaction;

	public CreateTransactionPreparedStatementWriter(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		int i = 1;
		preparedStatement.setString(i++, transaction.getUsernameFrom());
		preparedStatement.setString(i++, transaction.getUsernameTo());
		preparedStatement.setTimestamp(i++, Timestamp.valueOf(transaction.getDateAndTime()));
		preparedStatement.setLong(i++, transaction.getTransferredAmount());
		preparedStatement.setString(i++, transaction.getTransferComment());
	}

}