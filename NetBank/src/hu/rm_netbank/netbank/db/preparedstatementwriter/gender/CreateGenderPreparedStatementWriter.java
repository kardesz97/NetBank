package hu.rm_netbank.netbank.db.preparedstatementwriter.gender;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class CreateGenderPreparedStatementWriter implements Preparedstatementwriter<Gender> {

	private final Gender gender;

	public CreateGenderPreparedStatementWriter(Gender gender) {
		this.gender = gender;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		int i = 1;
		preparedStatement.setString(i++, gender.getName());
	}

}