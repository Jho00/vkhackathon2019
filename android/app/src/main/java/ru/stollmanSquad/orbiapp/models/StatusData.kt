package ru.stollmanSquad.orbiapp.models

data class StatusData<T>(
        val status : String,
        val data : T?
)