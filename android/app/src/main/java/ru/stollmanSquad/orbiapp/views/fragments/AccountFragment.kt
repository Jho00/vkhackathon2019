package ru.stollmanSquad.orbiapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.common.CommonStore
import ru.stollmanSquad.orbiapp.common.DataStore
import ru.stollmanSquad.orbiapp.views.MainActivity
import ru.stollmanSquad.orbiapp.views.fragments.navigator.NavigationHost

/**
 * Fragment representing the login screen for Shrine.
 */
class AccountFragment : Fragment() {

    var dataStore : DataStore? = null
    var nav : NavigationHost? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataStore = DataStore(context!!)
        nav = (activity as MainActivity)

        val view = inflater.inflate(R.layout.account_fragment, container, false)

        view.findViewById<TextView>(R.id.AccountMoney).text = "${dataStore!!.get(CommonStore.USER_MONEY, "0")} КОИНОВ"
        view.findViewById<TextView>(R.id.AccountLastName).text = dataStore!!.get(CommonStore.USER_LASTNAME)
        view.findViewById<TextView>(R.id.AccountName).text = dataStore!!.get(CommonStore.USER_NAME)

        val btn = view.findViewById<MaterialButton>(R.id.AccountLogout)
        btn.setOnClickListener {
            dataStore!!.put(CommonStore.USER_ID, "")
            dataStore!!.put(CommonStore.USER_TOKEN, "")
            dataStore!!.put(CommonStore.USER_NAME, "")
            dataStore!!.put(CommonStore.USER_LASTNAME, "")
            dataStore!!.put(CommonStore.USER_PHOTO_URL, "")
            dataStore!!.put(CommonStore.USER_MONEY, "")
            nav!!.navigateTo(LoginFragment(), false)
        }

        return view
    }
}
