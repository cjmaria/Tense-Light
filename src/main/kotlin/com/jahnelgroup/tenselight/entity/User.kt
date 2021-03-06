package com.jahnelgroup.tenselight.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(name = "firstname") var firstName: String = "",
    @Column(name = "lastname") var lastName: String = "",
    @Column(name = "email") var email: String = "",
    @Column(name = "isactive") var isActive: Boolean = true,
    @Column(name = "type") var type: Int = 1
) {
    fun hasIncompleteFields(): Boolean {
        return (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty())
    }

    override fun equals(other: Any?): Boolean {
        return (
            other is User &&
            id == other.id &&
            firstName == other.firstName &&
            lastName == other.lastName &&
            email == other.email &&
            isActive == other.isActive &&
            type == other.type
        )
    }
}
