package hu.rm_netbank.netbank.db.preparedstatementwriter.country;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.Country;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class CreateCountryPreparedStatementWriter implements Preparedstatementwriter<Country> {

	private final Country country;

	public CreateCountryPreparedStatementWriter(Country country) {
		this.country = country;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		int i = 1;
		preparedStatement.setString(i++, country.getName());
	}

}