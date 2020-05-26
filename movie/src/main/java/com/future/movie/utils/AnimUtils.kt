package com.future.movie.utils

import android.content.Context
import android.os.Build
import android.util.IntProperty
import android.util.Property
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator

object AnimUtils {

    private var fastOutSlowIn: Interpolator? = null

    fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                    android.R.interpolator.fast_out_slow_in)
        }
        return fastOutSlowIn
    }

    abstract class IntProp<T> protected constructor(val name: String) {

        abstract operator fun set(`object`: T, value: Int)

        abstract operator fun get(`object`: T): Int
    }

    fun <T> createIntProperty(impl: IntProp<T>): Property<T, Int> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            object : IntProperty<T>(impl.name) {
                override fun get(`object`: T): Int? {
                    return impl[`object`]
                }

                override fun setValue(`object`: T, value: Int) {
                    impl[`object`] = value
                }
            }
        } else {
            object : Property<T, Int>(Int::class.java, impl.name) {
                override fun get(`object`: T): Int? {
                    return impl[`object`]
                }

                override fun set(`object`: T, value: Int?) {
                    impl[`object`] = value!!
                }
            }
        }
    }
}
