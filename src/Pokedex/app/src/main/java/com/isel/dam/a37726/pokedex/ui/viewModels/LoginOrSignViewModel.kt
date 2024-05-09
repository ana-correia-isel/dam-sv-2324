package com.isel.dam.a37726.pokedex.ui.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginOrSignViewModel : ViewModel() {

    private var auth: FirebaseAuth

    private lateinit var user: FirebaseUser

    var email = MutableLiveData<String>()

    var password = MutableLiveData<String>()

    var isSuccessful = MutableLiveData<Boolean>()
    init {
        auth = Firebase.auth
    }

    fun getUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Navigate TO
            print("dd")
        }
    }

    fun createAccount(email: String, password:String) {
        /*email.value?.let {
            password.value?.let { it1 ->
                auth.createUserWithEmailAndPassword(it, it1)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Success", "createUserWithEmail:success")
                            val user = auth.currentUser
                        } else {
                            Log.w("Error", "createUserWithEmail:failure", task.exception)
                        }
                    }
            }
        }*/

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Success", "createUserWithEmail:success")
                    val user = auth.currentUser
                    isSuccessful.postValue(true)
                } else {
                    Log.w("Error", "createUserWithEmail:failure", task.exception)
                    isSuccessful.postValue(false)
                }
            }
    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithEmail:success")
                    val user = auth.currentUser
                    isSuccessful.postValue(true)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Error", "signInWithEmail:failure", task.exception)
                    isSuccessful.postValue(false)
                }
            }
    }
}