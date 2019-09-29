package ru.stollmanSquad.orbiapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.api.ChallengeApiService
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

        //Log.d("CHALLENGES", "view is ${view}")

        challengesList = view.findViewById<ListView>(R.id.ChallengesList)

        onReceiveChallenges(arrayListOf(
                Challenge("","Test","TETSTSTST", 100,"3 дня")
        ))

        val repository = ChallengeApiService.Factory.create()
        repository.getChellenge()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    val data = result.data
                    if(data != null)
                        onReceiveChallenges(data.toList())
                    else
                        onReceiveChallenges(arrayListOf())
                }, { error ->
                    error.printStackTrace()
                })

        return view
    }

    fun onReceiveChallenges(challenges : List<Challenge>){
        challengesList.adapter = ChallengesListAdapter(context!!, challenges)
    }

}
