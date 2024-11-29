package com.example.newsapp.presentation.Authentication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.R
import com.example.newsapp.data.model.AuthStatus
import com.example.newsapp.data.model.CardContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authStatus = MutableLiveData<AuthStatus>()
    val authStatus: LiveData<AuthStatus> = _authStatus

    private var databaseReference = FirebaseDatabase.getInstance().reference
    var currentUser = auth.currentUser
    var username = currentUser?.displayName

    init {
        checkAuthStatus()
    }
    fun checkAuthStatus()
    {
        if(auth.currentUser != null) {
            _authStatus.value = AuthStatus.Authenticated
        }
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
                    auth = FirebaseAuth.getInstance()
                    currentUser  = auth.currentUser
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
                    auth = FirebaseAuth.getInstance()
                    currentUser  = auth.currentUser
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
                    "email" to FirebaseAuth.getInstance().currentUser?.email,
                    "imageUri" to R.drawable.profile
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




fun saveArticle(article: CardContent, context: Context) {


    currentUser?.let { user ->
        val userArticlesRef = databaseReference.child("Users").child(user.uid).child("articles")

        // Query to check if the article already exists
        userArticlesRef.orderByChild("title").equalTo(article.title) // Assuming "title" is unique
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Duplicate found
                        Toast.makeText(context, "Article already saved", Toast.LENGTH_LONG).show()
                    } else {
                        // No duplicate found, save the article
                        val articleKey = userArticlesRef.push().key
                        if (articleKey != null) {
                            article.articleId = articleKey
                            userArticlesRef.child(articleKey).setValue(article)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful)
                                        Toast.makeText(context, "Article saved successfully", Toast.LENGTH_LONG).show()
                                    else
                                        Toast.makeText(context, "Article save failed", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}
fun fetchArticles(context: Context,onArticlesFetched: (List<CardContent>) -> Unit){
    currentUser?.let{user ->
        val userArticlesRef = databaseReference.child("Users").child(user.uid).child("articles")
        userArticlesRef.addListenerForSingleValueEvent( object :ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                    {
                        val articlesList = mutableListOf<CardContent>()

                        //iterate over snapshot children and map them to 'CardContent' object
                        snapshot.children.forEach { it -> // DataSnapshot Object
                            val article = it.getValue(CardContent::class.java)
                            article?.let { articlesList.add(it) }
                        }

                        //invoke the callback, pass the list of articles
                        onArticlesFetched(articlesList)
                    }else
                    {
                        Toast.makeText(context,"No articles found",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"Error ${error.message}",Toast.LENGTH_LONG).show()
                }
            }

        )
    }
}

    fun deleteArticle(articleId:String, context: Context)
    {
        currentUser?.let { user ->
            val userArticlesRef = databaseReference.child("Users").child(user.uid).child("articles").child(articleId)
            userArticlesRef.removeValue()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful)
                    {
                        Toast.makeText(context,"Article deleted successfully",Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(context,"Article delete failed",Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener{ exception ->
                    Toast.makeText(context,"Error: ${exception.message}",Toast.LENGTH_LONG).show()
                }
        }
    }
    fun fetchUserName(context: Context) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser
//        var username = ""
        currentUser?.let { user ->
            val userId = user.uid // Get the logged-in user's ID
            val userRef = databaseReference.child("Users").child(userId).child("username") // Navigate to the "username" field

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        username = snapshot.getValue(String::class.java).toString() // Fetch the username as a String
                    } else {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
        } ?: run {
            Toast.makeText(context, "User Name not logged in", Toast.LENGTH_LONG).show()
        }
    }





}