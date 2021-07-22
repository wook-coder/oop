package com.pratice.oop.exception;

import java.text.MessageFormat;

public final class Verify {

  private Verify() {
  }

  public static void verify(boolean expression) {
    verify(expression, "유효성검사에 실패했습니다.");
  }

  public static void verify(boolean expression, String pattern, Object... arguments) {
    verify(expression, MessageFormat.format(pattern, arguments));
  }

  public static void verify(boolean expression, String message) {
    if (expression) {
      return;
    }

    throw new VerifyException(message);
  }
}
