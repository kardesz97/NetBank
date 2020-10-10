package hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectAllByIdAndPasswordUserBalancePreparedStatementWriter implements Preparedstatementwriter<UserBalance> {

	private final String username;
	private final String passwordHash;

	public SelectAllByIdAndPasswordUserBalancePreparedStatementWriter(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, passwordHash);
	}

}