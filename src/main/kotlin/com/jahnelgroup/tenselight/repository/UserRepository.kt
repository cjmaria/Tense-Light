package com.jahnelgroup.tenselight.repository

import com.jahnelgroup.tenselight.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
