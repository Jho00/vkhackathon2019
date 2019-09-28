package ru.stollmanSquad.orbiapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.adapters.InfoListAdapter
import ru.stollmanSquad.orbiapp.models.Info


/**
 * Fragment representing the login screen for Shrine.
 */
class InfoFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.info_fragment, container, false)
        val arr = arrayListOf<Info>()
        arr.add(Info("","", R.drawable.infograph))
        for(i in 1..8){
            val title = getStringResourceByName("HelpTitle$i")
            val desc = getStringResourceByName("HelpText$i")
            arr.add(Info(title, desc))
        }

        //Log.d("KEKWTF", "$arr")

        view.findViewById<ListView>(R.id.InfoListView).adapter = InfoListAdapter(context!!, arr)
        return view
    }

    private fun getStringResourceByName(aString: String): String {
        val resId = resources.getIdentifier(aString, "string", "ru.stollmanSquad.orbiapp")
        return getString(resId)
    }
}
