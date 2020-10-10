package hu.rm_netbank.netbank.db.sqlbuilder.userbalance;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class UpdateUserBalanceSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "UPDATE user_balance SET first_name=?, last_name=?, email=?, password_hash=?, city_id=?, date_of_birth=?, gender_id=?, total_balance=?, change_date=?, intro_text=? WHERE username=? AND password_hash=?";
	}

}