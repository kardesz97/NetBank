package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.DummyPreparedStatmentWriter;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectTopTenUsers;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserBalanceDao userBalanceDao = new UserBalanceDao();
		userBalanceDao.openConnection();
		List<UserBalance> userBalances = userBalanceDao.read(new SelectTopTenUsers(), new DummyPreparedStatmentWriter(),
				new SelectAllUserBalanceResultSetReader());
		userBalanceDao.closeConnection();

		request.setAttribute("userBalances", userBalances);

		request.getRequestDispatcher("/auth/home.jsp")
				.forward(request, response);
	}

}