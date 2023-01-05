package com.john.warmcom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class TalkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_talk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.matchStatusTest1).setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("email", "yuki11835@gmail.com")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.AskProblemAskerButton).setOnClickListener {
            startActivity(Intent(context, AskQuestionActivity::class.java))
        }
    }
}