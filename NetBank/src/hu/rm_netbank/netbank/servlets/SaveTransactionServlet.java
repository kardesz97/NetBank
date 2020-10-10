package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.TransactionDao;
import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.Transaction;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.transaction.CreateTransactionPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.SelectAllByIdUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.UpdateUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.transaction.CreateTransactionSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.UpdateUserBalanceSqlBuilder;

public class SaveTransactionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usernameFrom = ((UserBalance) request.getSession()
				.getAttribute("loggedInUser")).getUsername();
		String usernameToAsAttribute = (String) request.getAttribute("usernameTo");
		String usernameToAsParameter = request.getParameter("usernameTo");
		String usernameTo = usernameToAsParameter == null ? usernameToAsAttribute : usernameToAsParameter;
		String amountToBeTransferredAsString = request.getParameter("amountToBeTransferred");
		String transferComment = request.getParameter("transferComment");
		long amountToBeTransferred = Long.parseLong(amountToBeTransferredAsString);

		LocalDateTime now = LocalDateTime.now();
		Transaction transaction = Transaction.builder()
				.withUsernameFrom(usernameFrom)
				.withUsernameTo(usernameTo)
				.withDateAndTime(now)
				.withTransferredAmount(amountToBeTransferred)
				.withTransferComment(transferComment)
				.build();

		TransactionDao transactionDao = new TransactionDao();
		transactionDao.openConnection();
		transactionDao.create(new CreateTransactionSqlBuilder(),
				new CreateTransactionPreparedStatementWriter(transaction));
		transactionDao.closeConnection();

		UserBalanceDao userBalanceDao = new UserBalanceDao();
		userBalanceDao.openConnection();
		UserBalance fromUserBalance = userBalanceDao
				.read(new SelectAllByIdUserBalanceSqlBuilder(),
						new SelectAllByIdUserBalancePreparedStatementWriter(usernameFrom),
						new SelectAllUserBalanceResultSetReader())
				.get(0);
		UserBalance toUserBalance = userBalanceDao
				.read(new SelectAllByIdUserBalanceSqlBuilder(),
						new SelectAllByIdUserBalancePreparedStatementWriter(usernameTo),
						new SelectAllUserBalanceResultSetReader())
				.get(0);
		UserBalance modifiedFromUserBalance = UserBalance.builder()
				.withUserName(fromUserBalance.getUsername())
				.withFirstName(fromUserBalance.getFirstName())
				.withLastName(fromUserBalance.getLastName())
				.withEmail(fromUserBalance.getEmail())
				.withPasswordHash(fromUserBalance.getPasswordHash())
				.withCityId(fromUserBalance.getCityId())
				.withDateOfBirth(fromUserBalance.getDateOfBirth())
				.withGenderId(fromUserBalance.getGenderId())
				.withTotalBalance(fromUserBalance.getTotalBalance() - amountToBeTransferred)
//				megnézni, hogy a mínusz után negatív lesz e
				.withChangeDate(now)
				.withIntroText(fromUserBalance.getIntroText())
				.build();

		UserBalance modifiedToUserBalance = UserBalance.builder()
				.withUserName(toUserBalance.getUsername())
				.withFirstName(toUserBalance.getFirstName())
				.withLastName(toUserBalance.getLastName())
				.withEmail(toUserBalance.getEmail())
				.withPasswordHash(toUserBalance.getPasswordHash())
				.withCityId(toUserBalance.getCityId())
				.withDateOfBirth(toUserBalance.getDateOfBirth())
				.withGenderId(toUserBalance.getGenderId())
				.withTotalBalance(toUserBalance.getTotalBalance() + amountToBeTransferred)
				.withChangeDate(now)
				.withIntroText(toUserBalance.getIntroText())
				.build();

		userBalanceDao.update(new UpdateUserBalanceSqlBuilder(),
				new UpdateUserBalancePreparedStatementWriter(modifiedFromUserBalance));
		userBalanceDao.update(new UpdateUserBalanceSqlBuilder(),
				new UpdateUserBalancePreparedStatementWriter(modifiedToUserBalance));

		userBalanceDao.closeConnection();

		request.setAttribute("username", usernameTo);
		request.getRequestDispatcher("/moneyTransfer")
				.forward(request, response);
	}

}