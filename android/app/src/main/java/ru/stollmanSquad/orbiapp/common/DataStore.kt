package ru.stollmanSquad.orbiapp.common

import android.content.Context
import android.content.SharedPreferences

public class DataStore(ctx: Context) {

    var prefs : SharedPreferences? = null

    init {
        prefs = ctx.getSharedPreferences("COMMON_STORE",Context.MODE_PRIVATE)
    }

    public fun put(key : String, data : String) {
        prefs!!.edit().putString(key, data).apply()
    }

    public fun get(key : String, defValue : String = "") : String {
        return prefs!!.getString(key, defValue)!!
    }


}