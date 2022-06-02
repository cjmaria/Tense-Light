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
    var firstName = ""

    @Column(name = "lastname")
    var lastName = ""

    @Column(name = "email")
    var email = ""

    @Column(name = "isactive")
    var isactive: Boolean = true

}
