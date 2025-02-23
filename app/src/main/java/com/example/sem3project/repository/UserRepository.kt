package com.example.sem3project.repository

import android.content.Context
import android.net.Uri
import com.example.sem3project.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

//    {
//     "success" : true
//    "message" : "login successfull"
//    }

    //    {
//     "success" : true
//    "message" : "register successfull"
//    "userId" : "1234"
//    }

    fun login(email:String,password:String,
              callback:(Boolean,String)->Unit)

    fun signup(email:String,password:String,
               callback: (Boolean, String,String) -> Unit)

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit)

    fun forgetPassword(email:String,
                       callback: (Boolean, String) -> Unit)

    fun getCurrentUser() : FirebaseUser?




}