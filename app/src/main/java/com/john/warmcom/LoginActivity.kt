package com.john.warmcom

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {
    val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val account: EditText = findViewById(R.id.AccountLogin)
        val password: EditText = findViewById(R.id.PasswordLogin)

        val auth = FirebaseAuth.getInstance()

        val login_button: Button = findViewById(R.id.LoginButton)
        val register_button: Button = findViewById(R.id.registerButton)

        login_button.setOnClickListener {
            val accountText = account.text.toString()
            val passwordText = password.text.toString()

            if (accountText == "" || passwordText == "") {
                Toast.makeText(this, "請輸入帳密", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                auth.signInWithEmailAndPassword(accountText, passwordText) // 使用信箱密碼登入
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show()
                            UserDetails.userUID = auth.currentUser?.uid
                            UserDetails.userEmail = accountText
                            if (accountText == "john6446500@gmail.com") {
                                UserDetails.userType = 1
                            } else {
                                UserDetails.userType = 2
                            }
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        register_button.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}