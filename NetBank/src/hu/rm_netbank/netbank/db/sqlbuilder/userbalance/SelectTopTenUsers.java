package hu.rm_netbank.netbank.db.sqlbuilder.userbalance;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectTopTenUsers implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT * FROM user_balance ORDER BY total_balance DESC LIMIT 10";
	}

}