package hu.rm_netbank.netbank.db.resultsetreader.transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.resultsetreader.ResultSetReader;

public class SelectAllTransactionResultSetReader implements ResultSetReader<Transaction> {

	@Override
	public List<Transaction> read(ResultSet resultSet) throws SQLException {
		List<Transaction> results = new ArrayList<>();
		while (resultSet.next()) {
			Long transactionId = resultSet.getLong("transaction_id");
			String usernameFrom = resultSet.getString("username_from");
			String usernameTo = resultSet.getString("username_to");
			LocalDateTime dateAndTime = resultSet.getTimestamp("date_and_time")
					.toLocalDateTime();
			Long transferredAmount = resultSet.getLong("transferred_amount");
			String transferComment = resultSet.getString("transfer_comment");

			Transaction transaction = Transaction.builder()
					.withTransactionId(transactionId)
					.withUsernameFrom(usernameFrom)
					.withUsernameTo(usernameTo)
					.withDateAndTime(dateAndTime)
					.withTransferredAmount(transferredAmount)
					.withTransferComment(transferComment)
					.build();

			results.add(transaction);
		}
		return results;
	}
}