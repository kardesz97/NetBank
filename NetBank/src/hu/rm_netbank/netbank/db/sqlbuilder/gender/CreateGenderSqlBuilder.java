package hu.rm_netbank.netbank.db.sqlbuilder.gender;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class CreateGenderSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "INSERT INTO gender (gender_id, name) VALUES (nextval('gender_seq'), ?)";
	}

}