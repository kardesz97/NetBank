package hu.rm_netbank.netbank.db.resultsetreader.city;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.resultsetreader.ResultSetReader;

public class SelectAllCityResultSetReader implements ResultSetReader<City> {

	@Override
	public List<City> read(ResultSet resultSet) throws SQLException {
		List<City> results = new ArrayList<>();
		while (resultSet.next()) {
			long cityId = resultSet.getLong("city_id");
			long countryId = resultSet.getLong("country_id");
			String name = resultSet.getString("name");

			City city = City.builder()
					.withCityId(cityId)
					.withCountryId(countryId)
					.withName(name)
					.build();

			results.add(city);
		}
		return results;
	}

}