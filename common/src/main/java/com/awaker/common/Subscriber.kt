package com.awaker.common

import android.util.Log
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription


class Subscriber<T> internal constructor() : Observer<T>, SingleObserver<T>,
        MaybeObserver<T>,
        CompletableObserver, org.reactivestreams.Subscriber<T> {

    override fun onSubscribe(d: Disposable) {}

    override fun onSuccess(t: T) {}

    override fun onSubscribe(s: Subscription) {
        s.request(Integer.MAX_VALUE.toLong())
    }

    override fun onNext(o: T) {}

    override fun onError(e: Throwable) {
        Log.e(TAG, e.toString())
    }

    override fun onComplete() {}

    companion object {

        private const val TAG = "Subscriber"

        @JvmStatic
        fun <T> create(): Subscriber<in T> {
            return Subscriber()
        }
    }
}