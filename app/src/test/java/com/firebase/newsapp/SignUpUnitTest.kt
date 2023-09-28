package com.firebase.newsapp

import domain.authorization.repository.AuthorizationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

class SignUpUnitTest {
private lateinit var repository: AuthorizationRepository

    @Test
    fun signUpUser() {
        val email = "4das3242323@gmail.com"
        val name = "Na"
        val password = "14233423423bwftw"
        val testingScope = CoroutineScope(Dispatchers.IO)
        testingScope.launch {
            repository.registration(email, password, name)
        }
    }



}