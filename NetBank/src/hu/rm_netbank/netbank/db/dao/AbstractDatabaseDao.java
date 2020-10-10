package hu.rm_netbank.netbank.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import hu.rm_netbank.netbank.NetBankRuntimeException;
import hu.rm_netbank.netbank.db.preparedstatementwriter.Preparedstatementwriter;
import hu.rm_netbank.netbank.db.resultsetreader.ResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.SqlBuilder;

public abstract class AbstractDatabaseDao<E> implements DatabaseDao<E> {

	protected Connection connection;

	@Override
	public void openConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/netbank", "netbank", "admin");
		} catch (SQLException | ClassNotFoundException e) {
			throw new NetBankRuntimeException("Az adatbázishoz való csatlakozás sikertelen.", e);
		}
	}

	@Override
	public void create(SqlBuilder sqlBuilder, Preparedstatementwriter<E> preparedStatementWriter) {
		try {
			String sql = sqlBuilder.buildSqlStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementWriter.write(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new NetBankRuntimeException("Hiba az adatbázisba történő adatbeszúrás közben.", e);
		}
	}

	@Override
	public <C> List<E> read(SqlBuilder sqlBuilder, Preparedstatementwriter<C> preparedStatementWriter,
			ResultSetReader<E> resultSetReader) {
		try {
			String sql = sqlBuilder.buildSqlStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementWriter.write(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<E> entities = resultSetReader.read(resultSet);
			return entities;
		} catch (SQLException e) {
			throw new NetBankRuntimeException("Hiba az adatbázisból történő adatlekérdezés közben.", e);
		}
	}

	@Override
	public <C> void update(SqlBuilder sqlBuilder, Preparedstatementwriter<C> preparedStatementWriter) {
		try {
			String sql = sqlBuilder.buildSqlStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementWriter.write(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new NetBankRuntimeException("Hiba az adatbázis módosítása közben.", e);
		}
	}

	@Override
	public <C> void delete(SqlBuilder sqlBuilder, Preparedstatementwriter<C> preparedStatementWriter) {
		try {
			String sql = sqlBuilder.buildSqlStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementWriter.write(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new NetBankRuntimeException("Hiba az adatbázisból történő törlés közben.", e);
		}
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new NetBankRuntimeException("Az adatbázishoz való csatlakozás nem sikerült lezárni.", e);
			}
		}
	}

}