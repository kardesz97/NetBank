package hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class CreateUserBalancePreparedStatementWriter implements Preparedstatementwriter<UserBalance> {

	private final UserBalance userBalance;

	public CreateUserBalancePreparedStatementWriter(UserBalance userBalance) {
		this.userBalance = userBalance;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		int i = 1;
		preparedStatement.setString(i++, userBalance.getUsername());
		preparedStatement.setString(i++, userBalance.getFirstName());
		preparedStatement.setString(i++, userBalance.getLastName());
		preparedStatement.setString(i++, userBalance.getEmail());
		preparedStatement.setString(i++, userBalance.getPasswordHash());
		preparedStatement.setLong(i++, userBalance.getCityId());
		preparedStatement.setDate(i++, Date.valueOf(userBalance.getDateOfBirth()));
		preparedStatement.setLong(i++, userBalance.getGenderId());
		preparedStatement.setLong(i++, userBalance.getTotalBalance());
		preparedStatement.setTimestamp(i++, Timestamp.valueOf(userBalance.getChangeDate()));
		preparedStatement.setString(i++, userBalance.getIntroText());
	}

}