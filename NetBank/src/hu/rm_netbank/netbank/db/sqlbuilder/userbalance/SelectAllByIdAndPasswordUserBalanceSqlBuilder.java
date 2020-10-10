package hu.rm_netbank.netbank.db.sqlbuilder.userbalance;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllByIdAndPasswordUserBalanceSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT * FROM user_balance WHERE username = ? AND password_hash = ?";
	}

}