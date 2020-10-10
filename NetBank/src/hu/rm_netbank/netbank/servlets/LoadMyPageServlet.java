package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.SelectAllByIdUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.util.RequestUtil;

public class LoadMyPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		
		UserBalanceDao userBalanceDao = new UserBalanceDao();
		userBalanceDao.openConnection();
		List<UserBalance> users = userBalanceDao.read(new SelectAllByIdUserBalanceSqlBuilder(),
				new SelectAllByIdUserBalancePreparedStatementWriter(username),
				new SelectAllUserBalanceResultSetReader());
		userBalanceDao.closeConnection();
		UserBalance user = users.get(0);

		RequestUtil.loadCityMap(request);
		RequestUtil.loadGenderMap(request);
		request.setAttribute("user", user);

		request.getRequestDispatcher("/auth/myPage.jsp")
				.forward(request, response);
	}
}