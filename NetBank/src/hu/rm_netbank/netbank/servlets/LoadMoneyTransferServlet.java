package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.TransactionDao;
import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.transaction.SelectAllByUsernameTransactionPreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.transaction.SelectAllTransactionResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.transaction.SelectAllByUsernameTransactionSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdUserBalanceSqlBuilder;

public class LoadMoneyTransferServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usernameFrom = ((UserBalance) request.getSession()
				.getAttribute("loggedInUser")).getUsername();
		String usernameTo = request.getParameter("username");
		if (usernameTo == null) {
			usernameTo = (String) request.getAttribute("username");
		}
		UserBalanceDao balanceDao = new UserBalanceDao();
		balanceDao.openConnection();
		UserBalance userToTransferTo = balanceDao
				.read(new SelectAllByIdUserBalanceSqlBuilder(),
						new SelectAllByUsernameTransactionPreparedStatementWriter(usernameTo),
						new SelectAllUserBalanceResultSetReader())
				.get(0);

		balanceDao.closeConnection();
		TransactionDao transactionDao = new TransactionDao();
		transactionDao.openConnection();
		List<Transaction> transactions = transactionDao.read(new SelectAllByUsernameTransactionSqlBuilder(),
				new SelectAllByUsernameTransactionPreparedStatementWriter(usernameFrom),
				new SelectAllTransactionResultSetReader());
		transactionDao.closeConnection();
//		Transaction user = transactions.get(0);

		request.setAttribute("transactions", transactions);
		request.setAttribute("userToTransferTo", userToTransferTo);
		request.getRequestDispatcher("/auth/moneyTransfer.jsp")
				.forward(request, response);
	}

}
