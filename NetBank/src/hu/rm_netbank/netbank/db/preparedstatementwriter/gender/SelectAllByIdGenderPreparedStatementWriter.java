package hu.rm_netbank.netbank.db.preparedstatementwriter.gender;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;

public class SelectAllByIdGenderPreparedStatementWriter implements Preparedstatementwriter<Gender> {

	private long genderId;

	public SelectAllByIdGenderPreparedStatementWriter(long genderId) {
		this.genderId = genderId;
	}

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setLong(1, genderId);
	}

}