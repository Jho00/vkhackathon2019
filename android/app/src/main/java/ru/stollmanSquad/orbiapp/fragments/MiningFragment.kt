package ru.stollmanSquad.orbiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.stollmanSquad.orbiapp.R

/**
 * Fragment representing the login screen for Shrine.
 */
class MiningFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.mining_fragment, container, false)
        return view
    }
}
