package hu.rm_netbank.netbank.db.sqlbuilder.country;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllByIdCountrySqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT country_id, name FROM country WHERE country_id = ?";
	}

}
