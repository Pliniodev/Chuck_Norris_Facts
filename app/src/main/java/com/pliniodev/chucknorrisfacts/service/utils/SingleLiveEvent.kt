package com.pliniodev.chucknorrisfacts.service.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * lifecycle-aware observable, chama o observable somente quando for feita
 * uma chamada explícita para call() ou setValue()
 *
 * Classe utilizada para:
 *  - Recuperar através do mPending se houve mensagem de erro. Se houver retorna true...
 *  - Retorna msgs de erro da classe AppResult
 */

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    //Só pode ser executado na MainThread
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(
                TAG,
                "true ONLY if there is at least one observer whose lifecycle state" +
                        " is STARTED or RESUMED"
            )
        }

        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Usado quando o T for um método do tipo void
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private val TAG = "SingleLiveEvent"
    }

}