package com.example.newsapp.presentation.Authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.AuthStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authStatus = MutableLiveData<AuthStatus>()

    val authStatus: LiveData<AuthStatus> = _authStatus

    init {
        CheckAuthStatus()
    }
    fun CheckAuthStatus()
    {
        if(auth.currentUser != null)
            _authStatus.value =  AuthStatus.Authenticated
        else
            _authStatus.value = AuthStatus.Unauthenticated
    }

    fun signIn(email:String,password:String)
    {
        _authStatus.value = AuthStatus.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful)
                {
                    _authStatus.value = AuthStatus.Authenticated
                    Log.d("AuthViewModel", "signInWithEmail:success")
                }
                else
                {
                    _authStatus.value = AuthStatus.Error(task.exception?.message ?: "Sign in failed")
                }
            }
    }
    fun signUp(email:String,password:String)
    {
        _authStatus.value = AuthStatus.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    _authStatus.value = AuthStatus.Authenticated
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    _authStatus.value = AuthStatus.Error(task.exception?.message ?: "Sign up failed")

                }
            }
    }
    fun signOut()
    {
        auth.signOut()
        _authStatus.value = AuthStatus.Unauthenticated
    }
    fun checkAndSaveUser(userId: String, username: String) {
        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)
        userRef.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                // If user does not exist, save the new user info
                val userMap = mapOf(
                    "username" to username,
                    "email" to FirebaseAuth.getInstance().currentUser?.email
                )
                userRef.setValue(userMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Successfully saved user info
                            Log.d("Username", "checkAndSaveUser: $username")
                        } else {
                            // Handle error
                        }
                    }
            } else {
                // User already exists, no need to save again
            }
        }.addOnFailureListener {
            // Handle error in retrieving data

        }
    }



}