package ru.stollmanSquad.orbiapp.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.stollmanSquad.orbiapp.models.Challenge
import ru.stollmanSquad.orbiapp.models.StatusData

interface ChallengeApiService {

    @GET("challenges")
    fun getChellenge() : Observable<StatusData<List<Challenge>>>
    @GET("challenges/my")
    fun getMyChallenges (@Query("user_id") user_id: String) : Observable<StatusData<List<Challenge>>>
    @GET("challenges/accept")
    fun getActiveChallenges (@Query("user_id") user_id: String) : Observable<StatusData<List<Challenge>>>
    @GET("challenges/join")
    fun joinChallenge (@Query("challenge_id") challenge_id: String,
                       @Query("user_id") user_id: String) : Observable<StatusData<String>>

    object Factory {

        val URL = "http://192.168.137.1:3000/"

        fun create(): ChallengeApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build()

            return retrofit.create(ChallengeApiService::class.java)
        }
    }
}