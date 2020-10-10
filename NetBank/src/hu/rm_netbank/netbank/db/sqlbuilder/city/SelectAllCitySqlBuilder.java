package hu.rm_netbank.netbank.db.sqlbuilder.city;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllCitySqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT city_id, country_id, name FROM city";
	}

}