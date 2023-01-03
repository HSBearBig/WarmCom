package com.john.warmcom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            auth.signOut()
            UserDetails.userUID = null
            Toast.makeText(context, "Sign Out Success!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finish()
        }

    }
}