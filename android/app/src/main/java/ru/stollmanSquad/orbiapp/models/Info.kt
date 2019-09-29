package ru.stollmanSquad.orbiapp.models

class Info(
        val title : String,
        val description : String,
        val drawableId : Int = 0
){
    override fun toString(): String {
        return "{\n" +
                "title : \"$title\",\n" +
                "description : \"$description\",\n" +
                "drawableId : $drawableId\n" +
                "}"
    }
}