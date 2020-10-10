package hu.rm_netbank.netbank.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import hu.rm_netbank.netbank.NetBankRuntimeException;
import hu.rm_netbank.netbank.db.dao.CityDao;
import hu.rm_netbank.netbank.db.dao.GenderDao;
import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.city.SelectAllByIdCityPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.gender.SelectAllByIdGenderPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.SelectAllByIdUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.city.SelectAllCityResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.gender.SelectAllGenderResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.userbalance.SelectAllUserBalanceResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.city.SelectAllByIdCitySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.gender.SelectAllByIdGenderSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.SelectAllByIdUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.util.DateUtil;
import hu.rm_netbank.netbank.util.PasswordHandler;

public class UserBalanceValidator {

	public String getPreviousValues(String username, String firstName, String lastName, String email,
			String cityIdAsString, String dateOfBirthAsString, String genderIdAsString, String introTextAsString) {
		try {
			StringJoiner joiner = new StringJoiner("&");
			joiner.add("username=" + URLEncoder.encode(username, "UTF-8"));
			joiner.add("firstName=" + URLEncoder.encode(firstName, "UTF-8"));
			joiner.add("lastName=" + URLEncoder.encode(lastName, "UTF-8"));
			joiner.add("email=" + URLEncoder.encode(email, "UTF-8"));
			joiner.add("city=" + URLEncoder.encode(cityIdAsString, "UTF-8"));
			joiner.add("dateOfBirth=" + URLEncoder.encode(dateOfBirthAsString, "UTF-8"));
			joiner.add("gender=" + URLEncoder.encode(genderIdAsString, "UTF-8"));
			joiner.add("introText=" + URLEncoder.encode(introTextAsString, "UTF-8"));

			return joiner.toString();
		} catch (UnsupportedEncodingException e) {
			throw new NetBankRuntimeException("Unable to encode parameters.", e);
		}
	}
	
	public String getPreviousValues(String username) {
		try {
			StringJoiner joiner = new StringJoiner("&");
			joiner.add("username=" + URLEncoder.encode(username, "UTF-8"));
			return joiner.toString();
		} catch (UnsupportedEncodingException e) {
			throw new NetBankRuntimeException("Unable to encode parameters.", e);
		}
	}

	public void validateIntroText(String introText, Set<String> invalidFields) {
		if (isInvalidLength(introText, 100)) {
			invalidFields.add("introTextInvalid");
		}
	}

	public void validateDateOfBirth(String dateOfBirthAsString, Set<String> invalidFields) {
		if (isInvalidLength(dateOfBirthAsString)) {
			invalidFields.add("dateOfBirthInvalid");
			return;
		}
		LocalDate dateOfBirth = null;
		try {
			dateOfBirth = DateUtil.convert(dateOfBirthAsString);
		} catch (DateTimeParseException e) {
			invalidFields.add("dateOfBirthInvalid");
			return;
		}
		if (dateOfBirth.isAfter(LocalDate.now()
				.minusYears(18L))) {
			invalidFields.add("dateOfBirthInvalid");
			return;
		}
	}

	public void validateGender(String genderIdAsString, Set<String> invalidFields) {
		if (isInvalidLength(genderIdAsString)) {
			invalidFields.add("genderInvalid");
			return;
		}
		long genderId = -1;
		try {
			genderId = Long.parseLong(genderIdAsString);
		} catch (NumberFormatException e) {
			invalidFields.add("genderInvalid");
			return;
		}
		GenderDao genderDao = new GenderDao();
		genderDao.openConnection();
		List<Gender> genders = genderDao.read(new SelectAllByIdGenderSqlBuilder(),
				new SelectAllByIdGenderPreparedStatementWriter(genderId), new SelectAllGenderResultSetReader());
		genderDao.closeConnection();
		if (genders.isEmpty()) {
			invalidFields.add("genderInvalid");
			return;
		}
	}

	public void validateCity(String cityIdAsString, Set<String> invalidFields) {
		if (isInvalidLength(cityIdAsString)) {
			invalidFields.add("cityInvalid");
			return;
		}
		long cityId = -1;
		try {
			cityId = Long.parseLong(cityIdAsString);
		} catch (NumberFormatException e) {
			invalidFields.add("cityInvalid");
			return;
		}
		CityDao cityDao = new CityDao();
		cityDao.openConnection();
		List<City> cities = cityDao.read(new SelectAllByIdCitySqlBuilder(),
				new SelectAllByIdCityPreparedStatementWriter(cityId), new SelectAllCityResultSetReader());
		cityDao.closeConnection();
		if (cities.isEmpty()) {
			invalidFields.add("cityInvalid");
			return;
		}
	}

	public void validateOldPassword(String username, String oldPassword, Set<String> invalidFields) {
		UserBalanceDao dao = new UserBalanceDao();
		dao.openConnection();
		List<UserBalance> userBalances = dao.read(new SelectAllByIdUserBalanceSqlBuilder(),
				new SelectAllByIdUserBalancePreparedStatementWriter(username),
				new SelectAllUserBalanceResultSetReader());
		dao.closeConnection();
		if (!userBalances.isEmpty()) {
			UserBalance userBalance = userBalances.get(0);
			String currentPasswordHash = ((UserBalance) userBalances).getPasswordHash();
			String oldPasswordHash = PasswordHandler.generateHash(oldPassword);
			if (!currentPasswordHash.equals(oldPasswordHash)) {
				invalidFields.add("oldPasswordInvalid");
				return;
			}
		}
	}

	public void validatePassword(String password, Set<String> invalidFields) {
		if (isInvalidLength(password, 8, 15) || !password
				.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*](?=\\S+$).{8,15}$")) {
			invalidFields.add("passwordInvalid");
		}
	}

	public void validatePasswordConfirm(String password, String passwordConfirm, Set<String> invalidFields) {
		if (isInvalidLength(passwordConfirm) || !passwordConfirm.equals(password)
				|| invalidFields.contains("passwordInvalid")) {
			invalidFields.add("passwordConfirmInvalid");
		}
	}

	public void validateEmail(String email, Set<String> invalidFields) {
		if (isInvalidLength(email, 100)) {
			invalidFields.add("emailInvalid");
		}
	}
	

	public void validateLastName(String lastName, Set<String> invalidFields) {
		if (isInvalidLength(lastName, 100)) {
			invalidFields.add("lastNameInvalid");
		}
	}

	public void validateFirstName(String firstName, Set<String> invalidFields) {
		if (isInvalidLength(firstName, 100)) {
			invalidFields.add("firstNameInvalid");
		}
	}

	public void validateUsername(String username, Set<String> invalidFields) {
		if (username == null || username.trim()
				.isEmpty() || username.length() < 3 || username.length() > 20) {
			invalidFields.add("usernameInvalid");
			return;
		}
		UserBalanceDao userBalanceDao = new UserBalanceDao();
		userBalanceDao.openConnection();
		List<UserBalance> userinfos = userBalanceDao.read(new SelectAllByIdUserBalanceSqlBuilder(),
				new SelectAllByIdUserBalancePreparedStatementWriter(username),
				new SelectAllUserBalanceResultSetReader());
		userBalanceDao.closeConnection();
		if (!userinfos.isEmpty()) {
			invalidFields.add("usernameInvalid");
			return;
		}
	}

	private boolean isInvalidLength(String field) {
		return field == null || field.trim()
				.isEmpty();
	}

	private boolean isInvalidLength(String field, int maxLength) {
		return isInvalidLength(field) || field.length() > maxLength;
	}

	private boolean isInvalidLength(String field, int minLength, int maxLength) {
		return isInvalidLength(field, maxLength) || field.length() < minLength;
	}

}