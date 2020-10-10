package hu.rm_netbank.netbank.db.sqlbuilder.message;

import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public class SelectAllByIdMessageSqlBuilder implements SqlBuilder {

	@Override
	public String buildSqlStatement() {
		return "SELECT message_id, username_from, username_to, date_and_time, message_text FROM message WHERE message_id = ?";
	}

}