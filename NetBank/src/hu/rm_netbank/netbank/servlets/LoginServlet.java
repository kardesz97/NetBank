package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.SelectAllByIdAndPasswordUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdAndPasswordUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.util.PasswordHandler;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordHash = PasswordHandler.generateHash(password);

		UserBalanceDao userBalanceDao = new UserBalanceDao();
		userBalanceDao.openConnection();
		List<UserBalance> userBalances = userBalanceDao.read(new SelectAllByIdAndPasswordUserBalanceSqlBuilder(),
				new SelectAllByIdAndPasswordUserBalancePreparedStatementWriter(username, passwordHash),
				new SelectAllUserBalanceResultSetReader());
		userBalanceDao.closeConnection();

		if (userBalances.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/login.jsp?invalidUsernameOrPassword=true");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", userBalances.get(0));
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

}