package hu.rm_netbank.netbank.db.sqlbuilder.transaction;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllTransactionSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT transaction_id, username_from, username_to, date_and_time, transferred_amount, transfer_comment FROM transaction";
	}

}