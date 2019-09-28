package ru.stollmanSquad.orbiapp.models

class Challenge(
        val name : String,
        val description : String,
        val money_pull : Int,
        val time_expired : String,
        val author_guid : String = "",
        val users : Array<String> = arrayOf(""),
        val user_count : Int =0,
        val status : String = ""
) {

}