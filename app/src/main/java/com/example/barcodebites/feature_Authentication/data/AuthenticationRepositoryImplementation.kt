package com.example.barcodebites.feature_Authentication.data

import android.content.Context
import android.util.Log
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.tasks.await
import kotlin.math.log

//sends a password reset email to the specified email address
class AuthenticationRepositoryImplementation(context: Context) : BaseRepositoryImplementation(context) {
    fun reset(email: String){
        auth.sendPasswordResetEmail(email) //von firebase
    }
    //creates a new user account with the specified email and password
    fun addUser(user: User) {
        auth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener{
            Log.d("USER",auth.currentUser.toString())
        }
    }
    //logs in a user with  the specified email and password, and returns an HTTP status code indicating the result of the login attempt
    suspend fun login(email: String, password: String): Int {
        return try {
            auth.signInWithEmailAndPassword(email,password).await()
            200
        } catch (e: FirebaseAuthInvalidCredentialsException){ //kein abstürzen der app
            403
        }
        catch (e: FirebaseTooManyRequestsException){
            429
        }
    }
    //removes the user from the device
    fun logout(){ removeUser() }

    //checks if a user is currently logged in on the device
    fun isLoggedIn(): Boolean {
        return checkUser()
    }
}