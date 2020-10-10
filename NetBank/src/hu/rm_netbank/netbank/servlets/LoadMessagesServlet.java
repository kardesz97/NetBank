package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.StringDao;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.message.SelectChatPartnersForUsernamePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.message.SelectChatPartnersForUsernameResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.message.SelectChatPartnersForUsernameSqlBuilder;

public class LoadMessagesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loggedInUsername = ((UserBalance) request.getSession()
				.getAttribute("loggedInUser")).getUsername();

		StringDao stringDao = new StringDao();
		stringDao.openConnection();
		List<String> usernames = stringDao.read(new SelectChatPartnersForUsernameSqlBuilder(), new SelectChatPartnersForUsernamePreparedStatementWriter(loggedInUsername), new SelectChatPartnersForUsernameResultSetReader());
		stringDao.closeConnection();

		request.setAttribute("usernames", usernames);
		request.getRequestDispatcher("/auth/messages.jsp")
				.forward(request, response);
	}

}