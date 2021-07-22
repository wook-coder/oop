package com.pratice.oop.exception;

public class NotFoundBookException extends BookStoreException {

  private static final long serialVersionUID = 6558003225251486639L;

  public NotFoundBookException() {
    super("이미 삭제 되었거나, 존재하지 않는 책입니다.");
  }

  public NotFoundBookException(final String message) {
    super(message);
  }

  public NotFoundBookException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public NotFoundBookException(final Throwable cause) {
    super(cause);
  }

  public NotFoundBookException(final String message,
                               final Throwable cause,
                               final boolean enableSuppression, final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
