package hu.rm_netbank.netbank;

public class NetBankRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NetBankRuntimeException() {
	}

	public NetBankRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NetBankRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetBankRuntimeException(String message) {
		super(message);
	}

	public NetBankRuntimeException(Throwable cause) {
		super(cause);
	}

}