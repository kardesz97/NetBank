package hu.rm_netbank.netbank.db.preparedstatementwriter.message;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectChatPartnersForUsernamePreparedStatementWriter implements Preparedstatementwriter<String> {

	private final String username;

	public SelectChatPartnersForUsernamePreparedStatementWriter(String username) {
		this.username = username;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, username);
	}

}