package com.jahnelgroup.tenselight.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jahnelgroup.tenselight.entity.User
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class UserControllerTest {
    @Autowired
    private lateinit var userController: UserController

    /* Initialize gson */
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    /* Load reference user list from JSON for checking tests */
    private val referenceUsersJson = File("src/test/resources/Users.json").readText(Charsets.UTF_8)
    private val usersListType = object : TypeToken<List<User>>() {}.type
    private val referenceUsersList: List<User> = gson.fromJson(referenceUsersJson, usersListType)

    @BeforeEach
    fun resetDatabase() {
        /* Reset DB with seeded data before each test */
        /* TODO: Find a way to pull config from build.grade.kts */
        val flyway = Flyway.configure().dataSource(
            "jdbc:mysql://127.0.0.1:3306/tenselight?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC",
            "root",
            "rootpassword"
        ).load()
        flyway.migrate()
    }

    @Test
    fun findAllTest() {
        val usersList = userController.findAll()
        Assertions.assertIterableEquals(usersList, referenceUsersList)
    }

    @Test
    fun findUserTest() {
        val expectedResponse = ResponseEntity.status(HttpStatus.OK).body(referenceUsersList[0])
        assert(userController.findUser(1) == expectedResponse)
        assert(userController.findUser(100)?.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun createUserTest() {
        var testUser = User(null, "Tony", "Tester", "tony@test.com")
        val userCreatedResponse = ResponseEntity.status(HttpStatus.CREATED).body(testUser)
        val createUserResponse = userController.createUser(testUser)
        assert(createUserResponse == userCreatedResponse)
        /* Test Dependency: relies on functional .findUser() to confirm the created user was persisted */
        /* Expecting test user to have null ID set to 9 from DB auto increment */
        testUser.id = 9
        assert(userController.findUser(9)?.body == testUser)
        /* Malformed user case */
        testUser = User(null, "Tony", "Tester", "")
        assert(userController.createUser(testUser).statusCode == HttpStatus.BAD_REQUEST)
    }

    @Test
    fun editUserTest() {
        var testUser = User(2, "Rida", "Rahman", "rida@example.com")
        /* Successful edit case */
        val userEditedResponse = ResponseEntity.status(HttpStatus.OK).body(testUser)
        assert(userController.editUser(2, testUser) == userEditedResponse)
        /* User not found case */
        testUser = User(10, "Rida", "Rahman", "rida@example.com")
        assert(userController.editUser(10, testUser).statusCode == HttpStatus.NOT_FOUND)
        /* User id mismatch in body case */
        testUser = User(2, "Rida", "Rahman", "rida@example.com")
        assert(userController.editUser(10, testUser).statusCode === HttpStatus.BAD_REQUEST)
        /* Malformed user case */
        testUser = User(2, "Rida", "", "rida@example.com")
        assert(userController.editUser(2, testUser).statusCode === HttpStatus.BAD_REQUEST)
    }

    @Test
    fun deleteUserTest() {
        /* Delete an existing user case */
        assert(userController.deleteUser(1).statusCode == HttpStatus.OK)
        /* Delete a non-existent user case */
        assert(userController.deleteUser(100).statusCode == HttpStatus.NOT_FOUND)
    }
}
