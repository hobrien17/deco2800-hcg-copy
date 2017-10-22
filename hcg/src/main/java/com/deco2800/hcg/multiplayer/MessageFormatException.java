package com.deco2800.hcg.multiplayer;

/**
 * An exception that is called due to an error in the format of a message.
 */
public class MessageFormatException extends Exception {

	public MessageFormatException() {
	}

	public MessageFormatException(String message) {
		super(message);
	}

	public MessageFormatException(Throwable cause) {
		super(cause);
	}

	public MessageFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
