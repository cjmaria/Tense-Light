package com.jahnelgroup.tenselight.controller

import com.jahnelgroup.tenselight.entity.User
import com.jahnelgroup.tenselight.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
    val userService: UserService
) {
    @GetMapping("/users")
    fun findAll(): List<User> {
        return userService.findAll()
    }

}