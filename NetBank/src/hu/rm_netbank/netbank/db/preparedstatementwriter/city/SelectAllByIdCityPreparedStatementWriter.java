package hu.rm_netbank.netbank.db.preparedstatementwriter.city;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectAllByIdCityPreparedStatementWriter implements Preparedstatementwriter<City> {

	private long cityId;

	public SelectAllByIdCityPreparedStatementWriter(long cityId) {
		this.cityId = cityId;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setLong(1, cityId);
	}

}