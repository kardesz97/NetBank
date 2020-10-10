package hu.rm_netbank.netbank.logic;

import java.util.List;
import java.util.Set;

import hu.rm_netbank.netbank.db.dao.GenderDao;
import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.preparedstatementwriter.gender.SelectAllByIdGenderPreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.gender.SelectAllGenderResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.gender.SelectAllByIdGenderSqlBuilder;

public class GenderValidator {

	private LengthValidator lengthValidator = new LengthValidator();

	public void validateGender(String genderIdAsString, Set<String> invalidFields) {
		if (lengthValidator.isInvalidLength(genderIdAsString)) {
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
		List<Gender> genders = genderDao.read(new SelectAllByIdGenderSqlBuilder(), new SelectAllByIdGenderPreparedStatementWriter(genderId), new SelectAllGenderResultSetReader());
		genderDao.closeConnection();
		if (genders.isEmpty()) {
			invalidFields.add("genderInvalid");
			return;
		}
		
	}

}