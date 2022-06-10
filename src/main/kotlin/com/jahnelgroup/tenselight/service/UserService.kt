package com.jahnelgroup.tenselight.service

import com.jahnelgroup.tenselight.entity.User
import com.jahnelgroup.tenselight.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

/* UserService methods assume well-formed arguments from the caller */
@Service
class UserService(
    val userRepository: UserRepository
) {
    /**
     * Finds all users
     * @return a list of all Users
     */
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    /**
     * Finds a user by ID
     * @property id a user id
     * @return an Optional containing the found User, if not empty
     */
    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    /**
     * Creates a new user
     * @property user a fully-initialized user with any value as id
     * @return the User as it appears in the database after insert
     */
    fun createUser(user: User): User {
        return userRepository.saveAndFlush(user)
    }

    /**
     * Edits a user
     * @property user a fully-initialized user with an id matching a user in the database
     * @return an Optional containing the User as it appears in the database after update, if not empty
     */
    fun editUser(user: User): Optional<User> {
        val dbUser = userRepository.findById(user.id!!).get().apply {
            firstName = user.firstName
            firstName = user.firstName
            lastName = user.lastName
            email = user.email
            isActive = user.isActive
            type = user.type
        }
        return Optional.of(userRepository.saveAndFlush(dbUser))
    }

    /**
     * Deletes a user given it's user id
     * @property id the user's id in the database
     * @return a Boolean of true if the user was deleted successfully, otherwise false
     */
    fun deleteUser(id: Long): Boolean {
        return userRepository.findById(id).map { foundUser ->
            userRepository.delete(foundUser)
            true
        }.orElse(false)
    }
}
