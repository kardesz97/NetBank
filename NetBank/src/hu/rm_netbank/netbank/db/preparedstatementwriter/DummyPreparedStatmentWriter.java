package hu.rm_netbank.netbank.db.preparedstatementwriter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DummyPreparedStatmentWriter implements Preparedstatementwriter<Object> {

	@Override
	public void write(PreparedStatement preparedStatement) throws SQLException {
	}

}
