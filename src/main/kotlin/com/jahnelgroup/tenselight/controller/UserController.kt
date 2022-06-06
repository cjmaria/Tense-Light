package com.jahnelgroup.tenselight.controller

import com.jahnelgroup.tenselight.entity.User
import com.jahnelgroup.tenselight.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UserController(
    val userService: UserService
) {
    @GetMapping("/users")
    fun findAll(): List<User> {
        return userService.findAll()
    }
    @GetMapping("/users/{id}")
    fun findUser(@PathVariable id: Long): ResponseEntity<Optional<User>> {
        val userResult: Optional<User> = userService.findById(id)

        if (userResult.isEmpty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResult)
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResult)
    }

    @PostMapping(
        path = ["/users"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUser(@RequestBody user: User): ResponseEntity<Optional<User>> {
        /* Ensure user has required fields */
        if (user.hasIncompleteFields()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.of(user))
        }
        val userResult: Optional<User> = Optional.of(userService.createUser(user))
        return ResponseEntity.status(HttpStatus.OK).body(userResult)
    }

    @PatchMapping(
        path = ["/users/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun editUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<Optional<User>> {
        /* Edits a user based on a user JSON which must include all complete fields
           and have a valid user id */
        if (user.hasIncompleteFields() || id != user.id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.of(user))
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(user))
    }

    @DeleteMapping(
        path = ["/users/{id}"],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Delete successful.")
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete failed.")
    }
}
