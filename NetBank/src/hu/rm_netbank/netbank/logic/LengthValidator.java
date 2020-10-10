package hu.rm_netbank.netbank.logic;

public class LengthValidator {

	public boolean isInvalidLength(String field) {
		return field == null || field.trim()
				.isEmpty();
	}

	public boolean isInvalidLength(String field, int maxLength) {
		return isInvalidLength(field) || field.length() > maxLength;
	}

	public boolean isInvalidLength(String field, int minLength, int maxLength) {
		return isInvalidLength(field, maxLength) || field.length() < minLength;
	}

}