package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor (
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun cleanup() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 작동한다.")
    fun saveBookTest() {
        //given
        val bookName = "이상한나라의 앨리스"
        val request = BookRequest(bookName)

        //when
        bookService.saveBook(request)

        //
        val result = bookRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo(bookName)
    }

    @Test
    @DisplayName("책 대출이 정상 작동한다.")
    fun loanBookTest() {
        //given
        bookRepository.save(Book("A-book"))
        val user = userRepository.save(User("Kim", 20))
        val bookLoanRequest = BookLoanRequest("Kim", "A-book")

        //when
        bookService.loanBook(bookLoanRequest)

        //then
        val result = userLoanHistoryRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].bookName).isEqualTo("A-book")
        assertThat(result[0].user.id).isEqualTo(user.id)
        assertThat(result[0].isReturn).isFalse()
    }

    @Test
    @DisplayName("책이 이미 대출되어있는 경우 실패한다.")
    fun loanBookFailTest() {
        //given
        bookRepository.save(Book("A-book"))
        val userA = userRepository.save(User("Kim", 20))
        userLoanHistoryRepository.save(
            UserLoanHistory(
                userA,
                "A-book",
                false
            )
        )
        val userB = userRepository.save(User("Lee", 20))
        val request = BookLoanRequest("Lee", "A-book")

        //when & then
        val errorMessage = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message

        assertThat(errorMessage).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반납이 정상 작동한다.")
    fun returnBookTest() {
        //given
        bookRepository.save(Book("A-book"))
        val userA = userRepository.save(User("Kim", 20))
        userLoanHistoryRepository.save(
            UserLoanHistory(
                userA,
                "A-book",
                false
            )
        )
        val returnRequest = BookReturnRequest("Kim", "A-book")

        //when
        bookService.returnBook(returnRequest)

        //then
        val result = userLoanHistoryRepository.findByBookNameAndIsReturn("A-book", true)
        assertThat(result).isNotNull
        assertThat(result).extracting("user").extracting("id").isEqualTo(userA.id)
        assertThat(result!!.bookName).isEqualTo("A-book")
        assertThat(result!!.isReturn).isTrue
    }
}
