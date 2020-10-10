package hu.rm_netbank.netbank.db.sqlbuilder.userbalance;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAscendingChangeDate implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT total_balance FROM user_balance ORDER BY change_date ASC";
	}

}