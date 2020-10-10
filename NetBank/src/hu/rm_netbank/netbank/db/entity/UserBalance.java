package hu.rm_netbank.netbank.db.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBalance {

	private final String username;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String passwordHash;
	private final Long cityId;
	private final LocalDate dateOfBirth;
	private final Long genderId;
	private final Long totalBalance;
	private final LocalDateTime changeDate;
	private final String introText;

	private UserBalance(Builder builder) {
		this.username = builder.userName;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.passwordHash = builder.passwordHash;
		this.cityId = builder.cityId;
		this.dateOfBirth = builder.dateOfBirth;
		this.genderId = builder.genderId;
		this.totalBalance = builder.totalBalance;
		this.changeDate = builder.changeDate;
		this.introText = builder.introText;
	}

	
	public static Builder builder() {
		return new Builder();
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Long getCityId() {
		return cityId;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Long getGenderId() {
		return genderId;
	}

	public Long getTotalBalance() {
		return totalBalance;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public String getIntroText() {
		return introText;
	}

	public static final class Builder {
		private String userName;
		private String firstName;
		private String lastName;
		private String email;
		private String passwordHash;
		private Long cityId;
		private LocalDate dateOfBirth;
		private Long genderId;
		private Long totalBalance;
		private LocalDateTime changeDate;
		private String introText;

		private Builder() {
		}

		public Builder withUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder withPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
			return this;
		}

		public Builder withCityId(Long cityId) {
			this.cityId = cityId;
			return this;
		}

		public Builder withDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder withGenderId(Long genderId) {
			this.genderId = genderId;
			return this;
		}

		public Builder withTotalBalance(Long totalBalance) {
			this.totalBalance = totalBalance;
			return this;
		}

		public Builder withChangeDate(LocalDateTime changeDate) {
			this.changeDate = changeDate;
			return this;
		}

		public Builder withIntroText(String introText) {
			this.introText = introText;
			return this;
		}

		public UserBalance build() {
			return new UserBalance(this);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Builder [userName=");
			builder.append(userName);
			builder.append(", firstName=");
			builder.append(firstName);
			builder.append(", lastName=");
			builder.append(lastName);
			builder.append(", email=");
			builder.append(email);
			builder.append(", passwordHash=");
			builder.append(passwordHash);
			builder.append(", cityId=");
			builder.append(cityId);
			builder.append(", dateOfBirth=");
			builder.append(dateOfBirth);
			builder.append(", genderId=");
			builder.append(genderId);
			builder.append(", totalBalance=");
			builder.append(totalBalance);
			builder.append(", changeDate=");
			builder.append(changeDate);
			builder.append(", introText=");
			builder.append(introText);
			builder.append("]");
			return builder.toString();
		}
	}

}
