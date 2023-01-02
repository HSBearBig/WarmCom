package com.john.warmcom

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sendMessage: EditText = findViewById(R.id.sendMessage)
        val sendButton: ImageButton = findViewById(R.id.sendButton)

        val db = Firebase.database
        var dbRef: DatabaseReference = db.getReference("messages")

        sendButton.setOnClickListener {
            val sendMessageText = sendMessage.text.toString()
            if (sendMessageText == "") {
                Toast.makeText(this, "請輸入內容", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                val map = mutableMapOf<String, String>()
                map["message"] = sendMessageText
                // TODO: Get User Data
                dbRef.push().setValue(map)
            }
        }


    }
}