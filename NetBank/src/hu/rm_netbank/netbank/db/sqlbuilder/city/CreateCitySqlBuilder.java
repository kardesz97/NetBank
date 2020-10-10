package hu.rm_netbank.netbank.db.sqlbuilder.city;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class CreateCitySqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "INSERT INTO city (city_id, country_id, name) VALUES (nextval('city_seq'), ?, ?)";
	}

}