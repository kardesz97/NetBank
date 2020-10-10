package hu.rm_netbank.netbank.db.resultsetreader.userbalance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.resultsetreader.ResultSetReader;

public class SelectAllUserBalanceResultSetReader implements ResultSetReader<UserBalance> {

	@Override
	public List<UserBalance> read(ResultSet resultSet) throws SQLException {
		List<UserBalance> results = new ArrayList<>();
		while (resultSet.next()) {
			String username = resultSet.getString("username");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			String passwordHash = resultSet.getString("password_hash");
			long cityId = resultSet.getLong("city_id");
			LocalDate dateOfBirth = resultSet.getDate("date_of_birth")
					.toLocalDate();
			long genderId = resultSet.getLong("gender_id");
			long totalBalance = resultSet.getLong("total_balance");
			LocalDateTime changeDate = resultSet.getTimestamp("change_date")
					.toLocalDateTime();
			String introText = resultSet.getString("intro_text");

			UserBalance userBalance = UserBalance.builder()
					.withUserName(username)
					.withFirstName(firstName)
					.withLastName(lastName)
					.withEmail(email)
					.withPasswordHash(passwordHash)
					.withCityId(cityId)
					.withDateOfBirth(dateOfBirth)
					.withGenderId(genderId)
					.withTotalBalance(totalBalance)
					.withChangeDate(changeDate)
					.withIntroText(introText)
					.build();

			results.add(userBalance);
		}
		return results;
	}

}