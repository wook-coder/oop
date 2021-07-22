package com.pratice.oop.bookstore.data.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.pratice.oop.bookstore.data.repository.BookRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookTest {

  @Autowired
  BookRepository bookRepository;

  @TestFactory
  @DisplayName("테스트 북스토어 저장소")
  Stream<DynamicTest> 북스토어_저장소() {
    return Stream.of(
        DynamicTest.dynamicTest("저장 할 수 있는가?", this::책_저장),
        DynamicTest.dynamicTest("저장 한 책의 이름, 제목, 설명이 유효한가?", this::저장_한_책_유효성검사),
        DynamicTest.dynamicTest("저장 한 책들의 목록을 조회할 수 있는가?", this::책_목록_조회),
        DynamicTest.dynamicTest("저장 한 책 중에 하나를 조회할 수 있는가?", this::책_조회),
        DynamicTest.dynamicTest("책을 수정할 수 있는가?", this::책_수정),
        DynamicTest.dynamicTest("수정 한 책의 이름, 제목, 설명, 수정일이 유효한가?", () -> {
        }),
        DynamicTest.dynamicTest("삭제 할 수 있는가?", () -> {
        }),
        DynamicTest.dynamicTest("삭제 한 책을 제외한 목룍을 조회할 수 있는가?", () -> {
        }),
        DynamicTest.dynamicTest("삭제 한 책을 조회하려고 할 때 '이미 삭제 된 책입니다.' 라고 에러 메시지를 응답하는가?", () -> {
        })
    );
  }

  private void 책_저장() {
    //given
    final Book book1 = Book.of("책이름1", "책설명1");
    final Book book2 = Book.of("책이름2", "책설명2");
    final Book book3 = Book.of("책이름3", "책설명3");
    final Book book4 = Book.of("책이름4", "책설명4");
    final Book book5 = Book.of("책이름5", "책설명5");

    //when
    final List<Book> actualBooks = List.of(bookRepository.save(book1),
                                           bookRepository.save(book2),
                                           bookRepository.save(book3),
                                           bookRepository.save(book4),
                                           bookRepository.save(book5));

    //then
    assertThat(actualBooks).isNotNull()
                           .isNotEmpty()
                           .filteredOn(book -> !book.isDeleteAt())
                           .containsAll(List.of(book1,
                                                book2,
                                                book3,
                                                book4,
                                                book5));
  }

  private void 저장_한_책_유효성검사() {
    //given
    final String title = "책이름1";
    final String description = "책설명1";
    final long id = bookRepository.save(Book.of(title, description)).getId();

    assertThat(id).isGreaterThan(0);
    assertDoesNotThrow(() -> {
      //when
      final Book actualBook = bookRepository.findByIdAndDeleteAtFalse(id)
                                            .orElseThrow(RuntimeException::new);

      //then
      assertThat(actualBook).isNotNull()
                            .extracting(Book::getTitle, Book::getDescription)
                            .isEqualTo(List.of(title, description));
    });
  }

  private void 책_목록_조회() {
    //when
    final List<Book> actualBooks = bookRepository.findBookByDeleteAtFalseOrderByIdDesc();

    assertThat(actualBooks).isNotNull()
                           .isNotEmpty()
                           .hasSize(6)
                           .isSortedAccordingTo(Comparator.comparingLong(Book::getId).reversed());
  }

  private void 책_조회() {
    final Book expectedBook = bookRepository.save(Book.of("책조회", "책 중에 하나를 조회 한다."));

    assertDoesNotThrow(() -> {
      final Book actualBook = bookRepository.findByIdAndDeleteAtFalse(expectedBook.getId())
                                            .orElseThrow(RuntimeException::new);

      assertThat(actualBook).isNotNull()
                            .extracting(Book::getTitle, Book::getDescription)
                            .describedAs("책 이름과 책 설명이 맞는지 비교")
                            .isEqualTo(List.of(expectedBook.getTitle(),
                                               expectedBook.getDescription()));
    });
  }

  private void 책_수정() {
    final Book expectedBook = bookRepository.save(Book.of("책 등록", "책 중에 하나를 등록한다."));
    final int beforeSize = bookRepository.findBookByDeleteAtFalseOrderByIdDesc().size();

    expectedBook.change("책 수정", "책 중에 하나를 수정한다.");

    final Book actualBook = bookRepository.save(expectedBook);
    final int afterSize = bookRepository.findBookByDeleteAtFalseOrderByIdDesc().size();

    assertThat(beforeSize).isEqualTo(afterSize);

    assertThat(actualBook).isNotNull()
                          .isEqualTo(expectedBook);

    assertThat(actualBook).extracting(Book::getUpdateTime)
                          .isNotNull();
  }
}