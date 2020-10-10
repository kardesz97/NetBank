package hu.rm_netbank.netbank.db.sqlbuilder.country;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class CreateCountrySqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "INSERT INTO country (country_id, name) VALUES (nextval('country_seq'), ?)";
	}

}