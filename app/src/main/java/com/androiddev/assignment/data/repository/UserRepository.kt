package com.androiddev.assignment.data.repository

import com.androiddev.assignment.data.local.UserDao
import com.androiddev.assignment.data.model.User

class UserRepository(private val userDao: UserDao) {

    // Check if user exists in the database by username
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getAllUsers():List<User>?{
        return  userDao.getAllUser()
    }
    // Save a new user to the database
    suspend fun saveNewUser(username: String, password: String) {
        val newUser = User(username = username, password = password) // Consider hashing the password!
        userDao.insert(newUser)
    }

}