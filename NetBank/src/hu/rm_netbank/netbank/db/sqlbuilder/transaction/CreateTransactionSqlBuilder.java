package hu.rm_netbank.netbank.db.sqlbuilder.transaction;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class CreateTransactionSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "INSERT INTO transaction (transaction_id, username_from, username_to, date_and_time, transferred_amount, transfer_comment) VALUES (nextval('transaction_seq'), ?, ?, ?, ?, ?)";
	}

}
