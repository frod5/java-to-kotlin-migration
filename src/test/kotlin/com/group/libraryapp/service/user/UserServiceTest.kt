package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @Test
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
}
