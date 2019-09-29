package ru.stollmanSquad.orbiapp.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKApiExecutionException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.stollmanSquad.orbiapp.R
import ru.stollmanSquad.orbiapp.api.UserApiService
import ru.stollmanSquad.orbiapp.common.CommonStore
import ru.stollmanSquad.orbiapp.common.DataStore
import ru.stollmanSquad.orbiapp.views.MainActivity
import ru.stollmanSquad.orbiapp.views.fragments.navigator.NavigationHost

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    var dataStore : DataStore? = null
    var nav : NavigationHost? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataStore = DataStore(context!!)
        nav = (activity as MainActivity)

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAuth()
    }

    fun checkAuth(){
        val id = dataStore!!.get(CommonStore.USER_ID)
        if(id.isNotEmpty()){
           nav!!.navigateTo(ChallengesFragment(), false)
            Log.d("VKSDK", "токен получен ${dataStore!!.get(CommonStore.USER_TOKEN)}")
            Log.d("VKSDK", "user получен ${dataStore!!.get(CommonStore.USER_ID)}")
        }else{


            val loginBtn = view!!.findViewById<com.google.android.material.button.MaterialButton>(R.id.LoginVKBtn)
            loginBtn.setOnClickListener{
                LoginVKBtnPress()
            }
        }
    }

    fun isLoginResult(requestCode: Int, resultCode: Int, data: Intent?) : Boolean{
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                loginRequested = false
                Log.d("VKSDK", "токен получен ${token.userId}")

                dataStore!!.put(CommonStore.USER_ID, token.userId.toString())
                dataStore!!.put(CommonStore.USER_TOKEN, token.accessToken)
                dataStore!!.put(CommonStore.USER_NAME, "")
                dataStore!!.put(CommonStore.USER_LASTNAME, "")
                dataStore!!.put(CommonStore.USER_PHOTO_URL, "")

                val userApi = UserApiService.Factory.create()
                userApi.authByToken(token.userId.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe (
                                { result ->
                                    Log.d("Login", "$result.data")
                                    userApi.userInfo(token.userId.toString())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe (
                                                    { result1 ->
                                                        dataStore!!.put(CommonStore.USER_MONEY, result1.data!!.money.toString())
                                                        nav!!.navigateTo(ChallengesFragment(), false)
                                                    },
                                            {
                                                error->error.printStackTrace()
                                                Toast.makeText(context, "Err ${error.message}", Toast.LENGTH_SHORT).show()
                                            }
                                            )
                                },

                                { error -> error.printStackTrace()
                                    Toast.makeText(context, "Err ${error.message}", Toast.LENGTH_SHORT).show()
                                })
            }

            override fun onLoginFailed(errorCode: Int) {
                loginRequested = false
                Toast.makeText(context, "Ошибка $errorCode", Toast.LENGTH_SHORT).show()
                Log.d("VKSDK", "код ошибки: $errorCode")
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            return false
        }

        return true
    }

    var loginRequested = false
    fun LoginVKBtnPress(){
        if(!loginRequested){
            loginRequested = true
            VK.login((activity as Activity), arrayListOf(VKScope.WALL))
        }
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
}
