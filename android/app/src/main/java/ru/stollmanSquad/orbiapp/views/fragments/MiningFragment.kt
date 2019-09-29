package ru.stollmanSquad.orbiapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.common.CommonStore
import ru.stollmanSquad.orbiapp.common.DataStore

/**
 * Fragment representing the login screen for Shrine.
 */
class MiningFragment : Fragment() {
    var dataStore : DataStore? = null
    var score = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataStore = DataStore(context!!)

        val view = inflater.inflate(R.layout.mining_fragment, container, false)

        score = Integer.parseInt(dataStore!!.get(CommonStore.USER_MONEY, "0"))

        val scoreView = view.findViewById<TextView>(R.id.MiningCoins)
        scoreView.text = "$score КОИНОВ"

        view.findViewById<Button>(R.id.MiningButton).setOnClickListener {
            score ++
            scoreView.text = "$score КОИНОВ"
            dataStore!!.put(CommonStore.USER_MONEY, score.toString())
        }

        return view
    }
}
