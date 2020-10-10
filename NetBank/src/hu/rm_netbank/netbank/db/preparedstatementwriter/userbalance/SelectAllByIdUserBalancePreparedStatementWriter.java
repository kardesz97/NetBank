package hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectAllByIdUserBalancePreparedStatementWriter implements Preparedstatementwriter<UserBalance> {

	private String username;

	public SelectAllByIdUserBalancePreparedStatementWriter(String username) {
		this.username = username;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, username);
	}

}