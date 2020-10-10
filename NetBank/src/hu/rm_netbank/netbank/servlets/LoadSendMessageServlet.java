package hu.rm_netbank.netbank.servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.MessageDao;
import hu.rm_netbank.netbank.db.entity.Message;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.message.SelectAllMessagesBetweenUsersPreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.message.SelectAllMessageResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.message.SelectAllMessagesBetweenUsersSqlBuilder;

public class LoadSendMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameFrom = ((UserBalance) request.getSession()
				.getAttribute("loggedInUser")).getUsername();
		String usernameTo = request.getParameter("username");
		if (usernameTo == null) {
			usernameTo = (String) request.getAttribute("username");
		}

		MessageDao messageDao = new MessageDao();
		messageDao.openConnection();
		List<Message> messages = messageDao.read(new SelectAllMessagesBetweenUsersSqlBuilder(), new SelectAllMessagesBetweenUsersPreparedStatementWriter(usernameFrom, usernameTo), new SelectAllMessageResultSetReader());
		messageDao.closeConnection();

		request.setAttribute("username", usernameTo);
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/auth/sendMessage.jsp")
				.forward(request, response);
	}

}