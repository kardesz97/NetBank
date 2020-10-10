package hu.rm_netbank.netbank.db.sqlbuilder.message;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllMessagesBetweenUsersSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT message_id, username_from, username_to, date_and_time, message_text FROM message WHERE (username_from = ? AND username_to = ?) OR (username_from = ? AND username_to = ?) ORDER BY date_and_time";
	}

}