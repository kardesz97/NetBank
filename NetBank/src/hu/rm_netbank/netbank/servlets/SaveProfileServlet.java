package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.SelectAllByIdUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.UpdateUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.UpdateUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.logic.UserBalanceValidator;
import hu.rm_netbank.netbank.util.DateUtil;
import hu.rm_netbank.netbank.util.PasswordHandler;

public class SaveProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String oldPassword = request.getParameter("oldPassword");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");
		String cityIdAsString = request.getParameter("city");
		String dateOfBirthAsString = request.getParameter("dateOfBirth");
		String genderIdAsString = request.getParameter("gender");
		String introTextAsString = request.getParameter("introText");

		UserBalanceValidator userBalanceValidator = new UserBalanceValidator();

		String previousValues = userBalanceValidator.getPreviousValues(username, firstName, lastName, email,
				cityIdAsString, dateOfBirthAsString, genderIdAsString, introTextAsString);

		Set<String> invalidFields = new HashSet<>();
		validate(userBalanceValidator, username, firstName, lastName, email, oldPassword, password, passwordConfirm,
				cityIdAsString, dateOfBirthAsString, genderIdAsString, introTextAsString, invalidFields);

		if (invalidFields.isEmpty()) {
			UserBalanceDao userBalanceDao = new UserBalanceDao();
			userBalanceDao.openConnection();
			List<UserBalance> userInfos = userBalanceDao.read(new SelectAllByIdUserBalanceSqlBuilder(),
					new SelectAllByIdUserBalancePreparedStatementWriter(username),
					new SelectAllUserBalanceResultSetReader());
			UserBalance oldUserInfo = userInfos.get(0);
			String oldPasswordHash = isPasswordChanging(oldPassword, password, passwordConfirm)
					? PasswordHandler.generateHash(oldPassword)
					: oldUserInfo.getPasswordHash();
			UserBalance userBalance = UserBalance.builder()
					.withUserName(username)
					.withFirstName(firstName)
					.withLastName(lastName)
					.withEmail(email)
					.withCityId(Long.parseLong(cityIdAsString))
					.withGenderId(Long.parseLong(genderIdAsString))
					.withPasswordHash(isPasswordChanging(oldPassword, password, passwordConfirm)
							? PasswordHandler.generateHash(password)
							: oldUserInfo.getPasswordHash())
					.withDateOfBirth(DateUtil.convert(dateOfBirthAsString))
					.withIntroText(introTextAsString)
					.build();
			userBalanceDao.update(new UpdateUserBalanceSqlBuilder(),
					new UpdateUserBalancePreparedStatementWriter(userBalance));
			userBalanceDao.closeConnection();
			request.getSession()
					.setAttribute("loggedInUser", userBalance);
			response.sendRedirect(request.getContextPath() + "/loadProfile?saveSuccessful");
		} else {
			StringJoiner invalidFieldJoiner = new StringJoiner("&", "&", "");
			for (String invalidField : invalidFields) {
				invalidFieldJoiner.add(invalidField + "=true");
			}
			response.sendRedirect(request.getContextPath() + "/loadProfile?" + previousValues + invalidFieldJoiner);
		}
	}

	private void validate(UserBalanceValidator userInfoValidator, String username, String firstName, String lastName,
			String email, String oldPassword, String password, String passwordConfirm, String cityIdAsString,
			String dateOfBirthAsString, String genderIdAsString, String introTextAsString, Set<String> invalidFields) {
		userInfoValidator.validateFirstName(firstName, invalidFields);
		userInfoValidator.validateLastName(lastName, invalidFields);
		if (isPasswordChanging(oldPassword, password, passwordConfirm)) {
			userInfoValidator.validateOldPassword(username, oldPassword, invalidFields);
			userInfoValidator.validatePassword(password, invalidFields);
			userInfoValidator.validatePasswordConfirm(password, passwordConfirm, invalidFields);
		}
		userInfoValidator.validateCity(cityIdAsString, invalidFields);
		userInfoValidator.validateDateOfBirth(dateOfBirthAsString, invalidFields);
		userInfoValidator.validateGender(genderIdAsString, invalidFields);
		userInfoValidator.validateIntroText(genderIdAsString, invalidFields);
	}

	private boolean isPasswordChanging(String oldPassword, String password, String passwordConfirm) {
		return oldPassword != null && !oldPassword.isBlank() || password != null && !password.isBlank()
				|| passwordConfirm != null && !passwordConfirm.isBlank();
	}

}