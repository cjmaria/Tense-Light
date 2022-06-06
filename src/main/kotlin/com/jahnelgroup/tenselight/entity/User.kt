package com.jahnelgroup.tenselight.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity

@Table(name = "users")

class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(name = "firstname")
    var firstName: String = ""

    @Column(name = "lastname")
    var lastName: String = ""

    @Column(name = "email")
    var email: String = ""

    @Column(name = "isactive")
    var isActive: Boolean = true

    @Column(name = "type")
    var type: Int = 1

    fun hasIncompleteFields(): Boolean {
        return (
            id == null ||
            firstName.isNullOrEmpty() ||
            lastName.isNullOrEmpty() ||
            email.isNullOrEmpty() ||
            isActive == null ||
            type == null
        )
    }
}
