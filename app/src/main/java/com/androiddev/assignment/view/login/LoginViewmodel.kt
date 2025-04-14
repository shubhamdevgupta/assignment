package com.androiddev.assignment.view.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.androiddev.assignment.data.datastore.DataStoreManager
import com.androiddev.assignment.data.local.AppDatabase
import com.androiddev.assignment.model.User
import com.androiddev.assignment.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "assignment-db"
    ).build()
    private val dataStoreManager =
        DataStoreManager(application.baseContext) // Pass context in ViewModel

    private val userRepository = UserRepository(db.userDao())

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    // Function to login or sign up a user
    fun loginUser() {
        viewModelScope.launch {
            if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
                // Check if user is new or existing
                if (isNewUser(username.value)) {
                    // Sign up logic
                    Log.d("LoginViewModel", "Signing up new user...")
                    // Save user to Room DB (example)
                    userRepository.saveNewUser(username.value, password.value)
                } else {
                    // Login logic
                    if (isValidUser(username.value, password.value)) {
                        Log.d("LoginViewModel", "User logged in successfully")
                    } else {
                        Log.d("LoginViewModel", "Invalid credentials!")
                    }
                }
            }
        }
    }


    // Function to check if the user is new
    suspend fun isNewUser(username: String): Boolean {
        val user = userRepository.getUserByUsername(username)
        return user == null // If user is null, it's a new user
    }

    // Function to check if the user credentials are valid
    suspend fun isValidUser(username: String, password: String): Boolean {
        val user = userRepository.getUserByUsername(username)
        return user?.password == password // Password matching check (Consider hashing passwords!)
    }

    suspend fun getAllUser(): List<User> ?{
        val users = userRepository.getAllUsers()
        return users
    }
}
