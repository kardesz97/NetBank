package hu.rm_netbank.netbank.db.preparedstatementwriter.city;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class CreateCityPreparedStatementWriter implements Preparedstatementwriter<City> {

	private final City city;

	public CreateCityPreparedStatementWriter(City city) {
		this.city = city;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		int i = 1;
		preparedStatement.setLong(i++, city.getCountryId());
		preparedStatement.setString(i++, city.getName());
	}

}