package ru.stollmanSquad.orbiapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.api.ChallengeApiService
import ru.stollmanSquad.orbiapp.common.CommonStore
import ru.stollmanSquad.orbiapp.common.DataStore
import ru.stollmanSquad.orbiapp.views.adapters.ChallengesListAdapter
import ru.stollmanSquad.orbiapp.models.Challenge

/**
 * Fragment representing the login screen for Shrine.
 */
class MainChallengesFragment(
) : Fragment() {


    private lateinit var challengesList : ListView
    private var currentMode : Boolean = true

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.main_challenges_fragment, container, false)

        challengesList = view.findViewById(R.id.ChallengesList)
        updateChallenges()

        view.findViewById<MaterialButton>(R.id.MainChallengesBtn).setOnClickListener {
            currentMode = true
            updateChallenges()
        }
        view.findViewById<MaterialButton>(R.id.ComplitedChallengesBtn).setOnClickListener {
            currentMode = false
            updateChallenges()
        }

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
        val dataStore = DataStore(context!!)
        val repository = ChallengeApiService.Factory.create()
        repository.getMyChallenges(dataStore.get(CommonStore.USER_ID))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    onReceiveChallenges(result.data!!.toList())
                }, { error ->
                    error.printStackTrace()
                })
    }
    private fun requestComplitedChallenges(){
        val dataStore = DataStore(context!!)
        val repository = ChallengeApiService.Factory.create()
        repository.getActiveChallenges(dataStore.get(CommonStore.USER_ID))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    onReceiveChallenges(result.data!!.toList())
                }, { error ->
                    error.printStackTrace()
                })
    }

    fun onReceiveChallenges(challenges : List<Challenge>){
        challengesList.adapter = ChallengesListAdapter(context!!, challenges)
    }

}
