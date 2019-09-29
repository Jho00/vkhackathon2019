package ru.stollmanSquad.orbiapp.common

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SimpleObserve<T>(
        val observable: Observable<T>,
        val onNext : Consumer<in T>,
        val onError : Consumer<in Throwable>
){

    init {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ( onNext, onError)
    }

}