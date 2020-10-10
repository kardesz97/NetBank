package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.MessageDao;
import hu.rm_netbank.netbank.db.entity.Message;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.message.CreateMessagePreparedStatementWriter;
import hu.rm_netbank.netbank.db.sqlbuilder.message.CreateMessageSqlBuilder;

public class SaveMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameFrom = ((UserBalance) request.getSession()
				.getAttribute("loggedInUser")).getUsername();
		String usernameTo = request.getParameter("usernameTo");
		String messageText = request.getParameter("message");

		Message message = Message.builder()
				.withUsernameFrom(usernameFrom)
				.withUsernameTo(usernameTo)
				.withMessageText(messageText)
				.withDateAndTime(LocalDateTime.now())
				.build();

		MessageDao messageDao = new MessageDao();
		messageDao.openConnection();
		messageDao.create(new CreateMessageSqlBuilder(), new CreateMessagePreparedStatementWriter(message));
		messageDao.closeConnection();

		request.setAttribute("username", usernameTo);
		request.getRequestDispatcher("/loadSendMessage")
				.forward(request, response);
	}

}