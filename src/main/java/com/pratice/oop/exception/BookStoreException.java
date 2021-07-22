package com.pratice.oop.exception;

public class BookStoreException extends RuntimeException {

  private static final long serialVersionUID = 5284844545688075694L;

  public BookStoreException() {
  }

  public BookStoreException(final String message) {
    super(message);
  }

  public BookStoreException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public BookStoreException(final Throwable cause) {
    super(cause);
  }

  public BookStoreException(final String message,
                            final Throwable cause,
                            final boolean enableSuppression,
                            final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
