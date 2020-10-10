package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.CreateUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.CreateUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.logic.UserBalanceValidator;
import hu.rm_netbank.netbank.util.DateUtil;
import hu.rm_netbank.netbank.util.PasswordHandler;

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");
		String cityIdAsString = request.getParameter("city");
		String dateOfBirthAsString = request.getParameter("dateOfBirth");
		String genderIdAsString = request.getParameter("gender");
		String changeDateAsString = request.getParameter("changeDate");
		String introTextAsString = request.getParameter("introText");

		UserBalanceValidator userBalanceValidator = new UserBalanceValidator();

		String previousValues = userBalanceValidator.getPreviousValues(username, firstName, lastName, email,
				cityIdAsString, dateOfBirthAsString, introTextAsString, genderIdAsString);

		Set<String> invalidFields = new HashSet<>();
		validate(userBalanceValidator, username, firstName, lastName, email, password, passwordConfirm, cityIdAsString,
				dateOfBirthAsString, genderIdAsString, changeDateAsString, introTextAsString, invalidFields);

		if (invalidFields.isEmpty()) {
			UserBalance userBalance = UserBalance.builder()
					.withUserName(username)
					.withFirstName(firstName)
					.withLastName(lastName)
					.withEmail(email)
					.withCityId(Long.parseLong(cityIdAsString))
					.withGenderId(Long.parseLong(genderIdAsString))
					.withPasswordHash(PasswordHandler.generateHash(password))
					.withDateOfBirth(DateUtil.convert(dateOfBirthAsString))
					.withTotalBalance(3000L)
					.withChangeDate(LocalDateTime.now())
					.withIntroText(introTextAsString)
					.build();
			UserBalanceDao userBalanceDao = new UserBalanceDao();
			userBalanceDao.openConnection();
			userBalanceDao.create(new CreateUserBalanceSqlBuilder(),
					new CreateUserBalancePreparedStatementWriter(userBalance));
			userBalanceDao.closeConnection();
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			StringJoiner invalidFieldJoiner = new StringJoiner("&", "&", "");
			for (String invalidField : invalidFields) {
				invalidFieldJoiner.add(invalidField + "=true");
			}
			response.sendRedirect(request.getContextPath() + "/registration?" + previousValues + invalidFieldJoiner);
		}
	}

	private void validate(UserBalanceValidator userBalanceValidator, String username, String firstName, String lastName,
			String email, String password, String passwordConfirm, String cityIdAsString, String dateOfBirthAsString,
			String genderIdAsString, String changeDateAsString, String introTextAsString, Set<String> invalidFields) {
		userBalanceValidator.validateUsername(username, invalidFields);
		userBalanceValidator.validateFirstName(firstName, invalidFields);
		userBalanceValidator.validateLastName(lastName, invalidFields);
		userBalanceValidator.validateEmail(email, invalidFields);
		userBalanceValidator.validatePassword(password, invalidFields);
		userBalanceValidator.validatePasswordConfirm(password, passwordConfirm, invalidFields);
		userBalanceValidator.validateCity(cityIdAsString, invalidFields);
		userBalanceValidator.validateGender(genderIdAsString, invalidFields);
		userBalanceValidator.validateDateOfBirth(dateOfBirthAsString, invalidFields);
		userBalanceValidator.validateIntroText(introTextAsString, invalidFields);
	}
}