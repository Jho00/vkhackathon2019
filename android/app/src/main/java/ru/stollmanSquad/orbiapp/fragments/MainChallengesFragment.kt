package ru.stollmanSquad.orbiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.adapters.ChallengesListAdapter
import ru.stollmanSquad.orbiapp.models.Challenge

/**
 * Fragment representing the login screen for Shrine.
 */
class MainChallengesFragment(
) : Fragment() {


    private lateinit var challengesList : ListView
    private var currentMode : Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        val view = inflater.inflate(R.layout.main_challenges_fragment, container, false)

        challengesList = view.findViewById(R.id.ChallengesList)
        updateChallenges()

        view.findViewById<MaterialButton>(R.id.MainChallengesBtn).setOnClickListener { requestMyChallenges()  }
        view.findViewById<MaterialButton>(R.id.ComplitedChallengesBtn).setOnClickListener { requestComplitedChallenges()  }

        return view
    }

    fun updateChallenges(){
        if(currentMode){
            requestMyChallenges()
        }else{
            requestComplitedChallenges()
        }
    }

    private fun requestMyChallenges(){
        //TODO: ("ADD: Request challenges ")
    }
    private fun requestComplitedChallenges(){
        //TODO: ("ADD: Request challenges ")
    }

    fun onReceiveChallenges(challenges : ArrayList<Challenge>){
        challengesList.adapter = ChallengesListAdapter(context!!, challenges)
    }

}
