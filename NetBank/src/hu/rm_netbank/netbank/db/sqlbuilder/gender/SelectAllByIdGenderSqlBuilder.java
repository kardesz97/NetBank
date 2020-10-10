package hu.rm_netbank.netbank.db.sqlbuilder.gender;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllByIdGenderSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT gender_id, name FROM gender WHERE gender_id = ?";
	}

}