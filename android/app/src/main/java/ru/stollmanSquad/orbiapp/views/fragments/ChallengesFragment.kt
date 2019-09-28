package ru.stollmanSquad.orbiapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.views.adapters.ChallengesListAdapter
import ru.stollmanSquad.orbiapp.models.Challenge

/**
 * Fragment representing the login screen for Shrine.
 */
class ChallengesFragment(
) : Fragment() {

    private lateinit var challengesList : ListView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        val view = inflater.inflate(R.layout.challenges_fragment, container, false)

        Log.d("CHALLENGES", "view is ${view}")

        challengesList = view.findViewById<ListView>(R.id.ChallengesList)

        //TODO: ("ADD: Request challenges ")

        onReceiveChallenges(arrayListOf(
                Challenge("Test","TETSTSTST", 100,"3 дня"),
                Challenge("Test1","TETSTSTST", 400,"3 дня"),
                Challenge("Test2","TETSTSTST", 100,"3 дня"),
                Challenge("Test3","TETSTSTST", 100,"3 дня"),
                Challenge("Test4","TETSTSTST", 100,"3 дня"),
                Challenge("Test5","TETSTSTST", 100,"3 дня"),
                Challenge("Test6","TETSTSTST", 100,"3 дня"),
                Challenge("Test7","TETSTSTST", 100,"3 дня")
        ))

        return view
    }

    fun onReceiveChallenges(challenges : ArrayList<Challenge>){
        challengesList.adapter = ChallengesListAdapter(context!!, challenges)
    }

}
