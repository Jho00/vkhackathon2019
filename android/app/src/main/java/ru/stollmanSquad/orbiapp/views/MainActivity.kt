package ru.stollmanSquad.orbiapp.views

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.api.UserApiService
import ru.stollmanSquad.orbiapp.common.CommonStore
import ru.stollmanSquad.orbiapp.common.DataStore
import ru.stollmanSquad.orbiapp.views.fragments.*
import ru.stollmanSquad.orbiapp.views.fragments.navigator.NavigationHost

class MainActivity : AppCompatActivity(), NavigationHost {

    private val logTAG: String = MainActivity::class.java.simpleName
    var currentFragment : Fragment? = null
    var bottomMenuNav : BottomNavigationView? = null
    val bottomMenuMap : HashMap<Int, Fragment> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setUpMenuBtnEvents()

        if (savedInstanceState == null) {
            navigateTo(LoginFragment(), false)
        }
        val data = DataStore(this)
        val uid = data.get(CommonStore.USER_ID)
        if(uid.isNotEmpty()){
            val userApi = UserApiService.Factory.create()
            userApi.authByToken(uid)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe (
                            { result ->
                                Log.d("Login", "$result.data")
                                userApi.userInfo(uid)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe (
                                                { result1 ->
                                                    data!!.put(CommonStore.USER_MONEY, result1.data!!.money.toString())
                                                },
                                                { error->error.printStackTrace()
                                                    Toast.makeText(baseContext, "Err ${error.message}", Toast.LENGTH_SHORT).show()
                                                }
                                        )
                            },

                            { error -> error.printStackTrace()
                                Toast.makeText(baseContext, "Err ${error.message}", Toast.LENGTH_SHORT).show()
                            })
        }

    }

    private fun setUpMenuBtnEvents(){
        bottomMenuMap[R.id.action_mining] = MiningFragment()
        bottomMenuMap[R.id.action_challenge] = ChallengesFragment()
        bottomMenuMap[R.id.action_my_challenges] = MainChallengesFragment()
        bottomMenuMap[R.id.action_account] = AccountFragment()
        bottomMenuMap[R.id.action_info] = InfoFragment()

        bottomMenuNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomMenuNav!!.setOnNavigationItemSelectedListener {
            val fragment : Fragment? = bottomMenuMap[it.itemId]
            if(currentFragment != fragment){
                navigateTo(fragment!!, false)
            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!(currentFragment is LoginFragment &&
            (currentFragment as LoginFragment).isLoginResult(requestCode, resultCode, data))){
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
                .replace(R.id.container, fragment, "MAIN_FRAGMENT")

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()


        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if(fragment is LoginFragment){
            nav.visibility = View.GONE
        }else{
            nav.visibility = View.VISIBLE
           // Log.d(logTAG, "view: ${fragment.javaClass.canonicalName}")
        }

        currentFragment = fragment

        for(p in bottomMenuMap){
            if(p.value.javaClass == currentFragment!!.javaClass && bottomMenuNav!!.selectedItemId != p.key){
                bottomMenuNav!!.selectedItemId = p.key
                break
            }
        }
    }
}
