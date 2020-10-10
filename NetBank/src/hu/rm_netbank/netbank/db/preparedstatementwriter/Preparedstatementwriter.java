package hu.rm_netbank.netbank.db.preparedstatementwriter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Preparedstatementwriter<E> {
	void write(PreparedStatement preparedStatement) throws SQLException;
}
