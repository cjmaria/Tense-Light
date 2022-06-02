package com.jahnelgroup.tenselight.service

import com.jahnelgroup.tenselight.entity.User
import com.jahnelgroup.tenselight.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun findAll(): List<User> {
        return userRepository.findAll()
    }
}
