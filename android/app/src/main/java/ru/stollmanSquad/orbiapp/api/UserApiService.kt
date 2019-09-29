package ru.stollmanSquad.orbiapp.api

import io.reactivex.Observable
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import ru.stollmanSquad.orbiapp.models.StatusData
import ru.stollmanSquad.orbiapp.models.User


interface UserApiService {

    @GET("users/mauth")
    fun authByToken(@Query("user_id") user_id: String) : Observable<StatusData<Any>>

    @GET("users/info")
    fun userInfo(@Query("user_id") user_id: String) : Observable<StatusData<User>>

    object Factory {

        val URL = "http://192.168.137.1:3000/"

        fun create(): UserApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build()

            return retrofit.create(UserApiService::class.java)
        }
    }
}