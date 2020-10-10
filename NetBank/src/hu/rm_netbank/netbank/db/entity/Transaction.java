package hu.rm_netbank.netbank.db.entity;

import java.time.LocalDateTime;

public class Transaction {

	private final Long transactionId;
	private final String usernameFrom;
	private final String usernameTo;
	private final LocalDateTime dateAndTime;
	private final Long transferredAmount;
	private final Long amountBefore;
	private final Long amountAfter;
	private final String transferComment;

	public Transaction(Long transactionId, String usernameFrom, String usernameTo, LocalDateTime dateAndTime, Long transferredAmount, Long amountBefore, Long amountAfter, String transferComment) {
		super();
		this.transactionId = transactionId;
		this.usernameFrom = usernameFrom;
		this.usernameTo = usernameTo;
		this.dateAndTime = dateAndTime;
		this.transferredAmount = transferredAmount;
		this.amountBefore = amountBefore;
		this.amountAfter = amountAfter;
		this.transferComment = transferComment;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public String getUsernameFrom() {
		return usernameFrom;
	}

	public String getUsernameTo() {
		return usernameTo;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public Long getTransferredAmount() {
		return transferredAmount;
	}

	public Long getAmountBefore() {
		return amountBefore;
	}

	public Long getAmountAfter() {
		return amountAfter;
	}

	public String getTransferComment() {
		return transferComment;
	}

	private Transaction(Builder builder) {
		this.transactionId = builder.transactionId;
		this.usernameFrom = builder.usernameFrom;
		this.usernameTo = builder.usernameTo;
		this.dateAndTime = builder.dateAndTime;
		this.transferredAmount = builder.transferredAmount;
		this.amountBefore = builder.amountBefore;
		this.amountAfter = builder.amountAfter;
		this.transferComment = builder.transferComment;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Long transactionId;
		private String usernameFrom;
		private String usernameTo;
		private LocalDateTime dateAndTime;
		private Long transferredAmount;
		private Long amountBefore;
		private Long amountAfter;
		private String transferComment;

		private Builder() {
		}

		public Builder withTransactionId(Long transactionId) {
			this.transactionId = transactionId;
			return this;
		}

		public Builder withUsernameFrom(String usernameFrom) {
			this.usernameFrom = usernameFrom;
			return this;
		}

		public Builder withUsernameTo(String usernameTo) {
			this.usernameTo = usernameTo;
			return this;
		}

		public Builder withDateAndTime(LocalDateTime dateAndTime) {
			this.dateAndTime = dateAndTime;
			return this;
		}

		public Builder withTransferredAmount(Long transferredAmount) {
			this.transferredAmount = transferredAmount;
			return this;
		}

		public Builder withAmountBefore(Long amountBefore) {
			this.amountBefore = amountBefore;
			return this;
		}

		public Builder withAmountAfter(Long amountAfter) {
			this.amountAfter = amountAfter;
			return this;
		}

		public Builder withTransferComment(String transferComment) {
			this.transferComment = transferComment;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Transaction [transactionId=");
		builder2.append(transactionId);
		builder2.append(", usernameFrom=");
		builder2.append(usernameFrom);
		builder2.append(", usernameTo=");
		builder2.append(usernameTo);
		builder2.append(", dateAndTime=");
		builder2.append(dateAndTime);
		builder2.append(", transferredAmount=");
		builder2.append(transferredAmount);
		builder2.append(", amountBefore=");
		builder2.append(amountBefore);
		builder2.append(", amountAfter=");
		builder2.append(amountAfter);
		builder2.append(", transferComment=");
		builder2.append(transferComment);
		builder2.append("]");
		return builder2.toString();
	}

}
