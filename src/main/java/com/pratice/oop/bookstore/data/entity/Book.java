package com.pratice.oop.bookstore.data.entity;

import static com.pratice.oop.exception.Verify.verify;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "book")
public class Book {

  private final static int DESCRIPTION_LENGTH = 1000;

  public static Book of(final String title, final String description) {
    return new Book(title, description, false, LocalDateTime.now());
  }

  private Book(final String title,
               final String description,
               final boolean deleteAt,
               final LocalDateTime saveTime) {

    this.title = title;
    this.description = description;
    this.deleteAt = deleteAt;
    this.saveTime = saveTime;
  }

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.TABLE)
  private long id;

  @Column(nullable = false)
  private String title;

  @Column(length = DESCRIPTION_LENGTH)
  private String description;

  @Column
  private boolean deleteAt;

  @Column(updatable = false)
  private LocalDateTime saveTime;

  @Column
  private LocalDateTime updateTime;

  public void change(final String title, final String description) {
    // TODO: 2021/07/18 도메인 객체쪽으로 유효성검사 옮겨야함.
    verify(Strings.isNotBlank(title), "{0}을 입력해주세요.", "책 이름");
    verify(Strings.isNotBlank(description), "{0}을 입력해주세요.", "책 설명");
    verify(description.length() <= DESCRIPTION_LENGTH, "{0}의 {1}자 내로 입력해주세요.", DESCRIPTION_LENGTH);

    this.title = title;
    this.description = description;
    this.updateTime = LocalDateTime.now();
  }
}
