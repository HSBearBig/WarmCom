package com.john.warmcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openChatRoom: Button = findViewById(R.id.openChatButton)
        openChatRoom.setOnClickListener {
            val newChatRoom = Intent(this, ChatActivity::class.java)
            startActivity(newChatRoom)
        }
    }
}