package hu.rm_netbank.netbank.db.dao;

import java.util.List;

import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;
import hu.rm_netbank.netbank.db.resultsetreader.ResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

/**
 * DAO = Data Access Object
 */
public interface DatabaseDao<E> {

	void openConnection();

	// CRUD

	void create(SqlBuilder sqlBuilder, Preparedstatementwriter<E> preparedStatementWriter);

	<C> List<E> read(SqlBuilder sqlBuilder, Preparedstatementwriter<C> preparedStatementWriter,
			ResultSetReader<E> resultSetReader);

	<C> void update(SqlBuilder sqlBuilder, Preparedstatementwriter<C> preparedStatementWriter);

	<C> void delete(SqlBuilder sqlBuilder, Preparedstatementwriter<C> preparedStatementWriter);

	void closeConnection();

}