package com.group.libraryapp.service.user

import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
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
        userRepository.saveAll(listOf(
            User("kim", 30),
            User("sung", 20)
        ))

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
}
