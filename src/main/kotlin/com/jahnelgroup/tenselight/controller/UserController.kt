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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {
    @GetMapping
    fun findAll(): List<User> {
        return userService.findAll()
    }
    @GetMapping("/{id}")
    fun findUser(@PathVariable id: Long): ResponseEntity<User>? {
        return userService.findById(id)
            .map { foundUser -> ResponseEntity.status(HttpStatus.OK).body(foundUser) }
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUser(@RequestBody user: User): ResponseEntity<Any> {
        if (user.hasIncompleteFields()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("User has incomplete fields"))
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user))
    }

    @PatchMapping(
        path = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun editUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<Any> {
        if (user.hasIncompleteFields() || id != user.id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has incomplete fields or id mismatch")
        }
        if (user.id?.let { userService.findById(it).isEmpty } == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(user).get())
    }

    @DeleteMapping(
        path = ["/{id}"],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Delete successful.")
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed.")
    }
}
