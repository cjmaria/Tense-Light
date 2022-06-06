package com.jahnelgroup.tenselight.service

import com.jahnelgroup.tenselight.entity.User
import com.jahnelgroup.tenselight.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    fun createUser(user: User): User {
        return userRepository.saveAndFlush(user)
    }

    fun editUser(user: User): Optional<User> {
        val optionalUser: Optional<User> = userRepository.findById(user.id)
        if (optionalUser.isEmpty) {
            return optionalUser
        }
        val dbUser: User = optionalUser.get()
        dbUser.firstName = user.firstName
        dbUser.lastName = user.lastName
        dbUser.email = user.email
        dbUser.isActive = user.isActive
        dbUser.type = user.type
        return Optional.of(userRepository.saveAndFlush(dbUser))
    }

    fun deleteUser(id: Long): Boolean {
        val optionalUser: Optional<User> = userRepository.findById(id)
        if (optionalUser.isEmpty) {
            return false
        }
        userRepository.delete(optionalUser.get())
        return true
    }
}
