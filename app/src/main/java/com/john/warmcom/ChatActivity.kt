package com.john.warmcom

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.Socket

class ChatActivity: AppCompatActivity() {
    private val host = "104.245.33.30"
    private val serverPort = 8001

    private var layout: LinearLayout? = null
    private var scrollView: ScrollView? = null
    private var bw: BufferedWriter? = null
    private var br: BufferedReader? = null
    private var clientSocket: Socket? = null
    private var type: Int = 0
    var message: String? = null
    private var counselorEmail: String? = null
    private var askerEmail: String? = null

    private val askerConnection = Runnable {
        var tmp: String;
        try {
            val serverIp: InetAddress = InetAddress.getByName(host)
            clientSocket = Socket(serverIp, serverPort)
            br = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
            bw = BufferedWriter(OutputStreamWriter(clientSocket!!.getOutputStream()))
            // Ask if email exist and connected
            bw!!.write(counselorEmail + "\n")
            bw!!.flush()

            do {
                tmp = br!!.readLine().toString()
            } while (tmp != "OK")

            while (clientSocket?.isConnected == true) {
                tmp = br!!.readLine().toString()
                message = tmp
                type = 2
                Handler(Looper.getMainLooper()).post(addMessageBoxThread)
            }
        } catch (e: Exception) {

        }
    }

    private val counselorConnection = Runnable {
        var tmp: String
        try {
            val serverIp = InetAddress.getByName(host)
            clientSocket = Socket(serverIp, serverPort)
            br = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
            bw = BufferedWriter(OutputStreamWriter(clientSocket!!.getOutputStream()))
//            bw!!.write(askerEmail + "\n")
//            bw!!.flush()

            while (clientSocket?.isConnected == true) {
                tmp = br!!.readLine().toString()
                message = tmp
                type = 2
                Handler(Looper.getMainLooper()).post(addMessageBoxThread)
            }
        } catch (e: Exception) {

        }
    }

    private val sendData = Runnable {
        try {
            bw!!.write(message + "\n")
            bw!!.flush()
        } catch (e: Exception) {

        }
    }

    private val addMessageBoxThread = Runnable {
        addMessageBox()
    }

    private fun addMessageBox() {
        val textView = TextView(this)
        textView.text = message
        if (type == 1) {
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(850, 10, 20, 20)
            textView.layoutParams = lp
        } else {
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(20, 10, 20, 20)
            textView.layoutParams = lp
        }

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1)
        } else {
            textView.setBackgroundResource(R.drawable.rounded_corner2)
        }
        type = 0

        layout?.addView(textView)
        scrollView?.fullScroll(View.FOCUS_DOWN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        if (UserDetails.userType == 1) {
            counselorEmail = intent.getStringExtra("email")
        } else {
            askerEmail = intent.getStringExtra("email")
        }

        val sendMessage: EditText = findViewById(R.id.sendMessage)
        val sendButton: ImageButton = findViewById(R.id.sendButton)
        layout = findViewById(R.id.chatLayout)
        scrollView = findViewById(R.id.chatScrollView)

        if (UserDetails.userType == 1) {
            val thread: Thread = Thread(askerConnection)
            thread.start()
        } else {
            val thread: Thread = Thread(counselorConnection)
            thread.start()
        }

        sendButton.setOnClickListener {
            message = sendMessage.text.toString()
            type = 1
            val sendDataThread = Thread(sendData)
            sendDataThread.start()
            addMessageBox()
            sendMessage.text.clear()
        }

    }
}