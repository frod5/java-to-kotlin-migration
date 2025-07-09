package com.group.libraryapp.service.user

import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun cleanup() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장이 정상 작동 한다")
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("kim", 30)

        //when
        userService.saveUser(request)

        //then
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(1)
        assertThat(userList[0].name).isEqualTo("kim")
    }

    @Test
    @DisplayName("유저 조회 정상 작동")
    fun getUserTest() {
        //given
        userRepository.saveAll(
            listOf(
                User("kim", 30),
                User("sung", 20)
            )
        )

        //when
        val users = userService.getUsers()

        //then
        assertThat(users).hasSize(2)
        assertThat(users).extracting("name").containsExactlyInAnyOrder("kim", "sung")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(30, 20)
    }

    @Test
    @DisplayName("유저 갱신 정상 작동")
    fun updateUsernameTest() {
        //given
        val savedUser = userRepository.save(User("kim", 30))
        val updateRequest = UserUpdateRequest(savedUser.id!!, "sung")

        //when
        userService.updateUserName(updateRequest)

        //then
        val results = userRepository.findAll()[0]
        assertThat(results.name).isEqualTo("sung")
        assertThat(results.age).isEqualTo(30)
    }

    @Test
    @DisplayName("유저 삭제 정상 작동")
    fun deleteUserTest() {
        //given
        userRepository.save(User("sung", 20))

        //when
        userService.deleteUser("sung")

        //then
        val results = userRepository.findAll()
        assertThat(results).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 경우도 응답에 포함된다")
    fun getUserLoanHistoriesTest1() {
        //given
        userRepository.save(User("kim", 30))

        //when
        val results = userService.getUserLoanHistories()

        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("kim")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 작동한다")
    fun getUserLoanHistoriesTest2() {
        //given
        val savedUser = userRepository.save(User("kim", 30))

        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED)
            )
        )

        //when
        val results = userService.getUserLoanHistories()

        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("kim")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name").containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn").containsExactlyInAnyOrder(false, false, true)
    }
}
