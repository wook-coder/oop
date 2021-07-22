package com.pratice.oop.exception;

public class VerifyException extends RuntimeException {

  private static final long serialVersionUID = 4095303737788978404L;

  public VerifyException() {
  }

  public VerifyException(final String message) {
    super(message);
  }

  public VerifyException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public VerifyException(final Throwable cause) {
    super(cause);
  }

  public VerifyException(final String message,
                         final Throwable cause,
                         final boolean enableSuppression,
                         final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
