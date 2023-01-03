package com.john.warmcom

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {
    val TAG = "RegisterActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val account: EditText = findViewById(R.id.AccountLogin)
        val password: EditText = findViewById(R.id.PasswordLogin)

        val auth = FirebaseAuth.getInstance()

        val register_button: Button = findViewById(R.id.registerButton)

        register_button.setOnClickListener {
            val accountText = account.text.toString()
            val passwordText = password.text.toString()

            if (accountText == "" || passwordText == "") {
                Toast.makeText(this, "請輸入帳密", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                auth.createUserWithEmailAndPassword(accountText, passwordText) // Firebase 用信箱密碼建立帳號
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show()
                            UserDetails.userUID = auth.currentUser?.uid
                            finish()
                        } else {
                            Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}