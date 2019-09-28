package ru.stollmanSquad.orbiapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.stollmanSquad.orbiapp.fragments.*
import ru.stollmanSquad.orbiapp.fragments.navigator.NavigationHost

class MainActivity : AppCompatActivity(), NavigationHost {

    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            navigateTo(LoginFragment(), false)
        }
        setUpMenuBtnEvents()
    }

    private fun setUpMenuBtnEvents(){
        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_mining -> {
                    navigateTo(MiningFragment(), false)
                }
                R.id.action_challenge -> {
                    navigateTo(ChallengesFragment(), false)
                }
                R.id.action_my_challenges ->{
                    navigateTo(MainChallengesFragment(), false)
                }
                R.id.action_account -> {
                    navigateTo(AccountFragment(), false)
                }
                R.id.action_info -> {
                    navigateTo(InfoFragment(), false)
                }
            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d(TAG, "токен получен: ${token.accessToken}")
                navigateTo(ChallengesFragment(), false)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(baseContext, "Ошибка", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "код ошибки: $errorCode")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()


        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if(fragment is LoginFragment){
            nav.visibility = View.GONE
        }else{
            nav.visibility = View.VISIBLE
            Log.d(TAG, "view: ${fragment.javaClass.canonicalName}")
        }
    }
}
