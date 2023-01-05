package com.john.warmcom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    private val connectLogin = Runnable {
        var reqParam = URLEncoder.encode("account", "UTF-8") + "=" + URLEncoder.encode("john6446500", "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("Test123", "UTF-8")

        val mURL = URL("http://104.245.33.30:8051/api/login/?" + reqParam)

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            Log.d("SettingFragment", "URL : $url")
            Log.d("SettingFragment", "Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                Log.d("SettingFragment", "Response : $response")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.Coinbtn).setOnClickListener {
            val thread = Thread(connectLogin)
            thread.start()
        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            auth.signOut()
            UserDetails.userUID = null
            Toast.makeText(context, "Sign Out Success!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finish()
        }

        view.findViewById<Button>(R.id.Coinbtn).setOnClickListener {
            startActivity(Intent(context, Coin::class.java))
        }

    }
}