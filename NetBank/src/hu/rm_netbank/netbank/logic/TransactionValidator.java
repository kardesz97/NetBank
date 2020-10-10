package hu.rm_netbank.netbank.logic;

import java.util.List;
import java.util.Set;

import hu.rm_netbank.netbank.db.dao.TransactionDao;
import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.transaction.SelectAllByIdTransactionPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.SelectAllByIdUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.transaction.SelectAllTransactionResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.transaction.SelectAllByIdTransactionSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectTotalBalanceByUsernameSqlBuilder;

public class TransactionValidator {

	private LengthValidator lengthValidator = new LengthValidator();

	public void validateTransaction(String transactionIdAsString, Set<String> invalidFields) {
		if (lengthValidator.isInvalidLength(transactionIdAsString)) {
			invalidFields.add("transactionInvalid");
			return;
		}
		long transactionId = -1;
		try {
			transactionId = Long.parseLong(transactionIdAsString);
		} catch (NumberFormatException e) {
			invalidFields.add("transactionInvalid");
			return;
		}
		TransactionDao transactionDao = new TransactionDao();
		transactionDao.openConnection();
		List<Transaction> transaction = transactionDao.read(new SelectAllByIdTransactionSqlBuilder(),
				new SelectAllByIdTransactionPreparedStatementWriter(transactionId),
				new SelectAllTransactionResultSetReader());
		transactionDao.closeConnection();
		if (transaction.isEmpty()) {
			invalidFields.add("transactionInvalid");
			return;
		}

	}
	
	public void validateAmount(String transferredAmountAsString, String username, Set<String> invalidAmount) {
		long transferredAmount = -1;
		try {
			transferredAmount = Long.parseLong(transferredAmountAsString);
		} catch (NumberFormatException e) {
			invalidAmount.add("transferInvalid");
			return;
		}
		UserBalanceDao userBalanceDao = new UserBalanceDao();
		userBalanceDao.openConnection();
		List<UserBalance> totalAmount = userBalanceDao.read(new SelectTotalBalanceByUsernameSqlBuilder(),
				new SelectAllByIdUserBalancePreparedStatementWriter(username),
				new SelectAllUserBalanceResultSetReader());
		userBalanceDao.closeConnection();
		if (!totalAmount.isEmpty()) {
			UserBalance userAmount = totalAmount.get(0);
			Long userTotalAmount = ((UserBalance) totalAmount).getTotalBalance();
			if (transferredAmount > userTotalAmount) {
				invalidAmount.add("transferInvalid");
				return;
			}
		}
		
	}
	
}
