package com.future.movie.utils

import android.view.View
import android.view.ViewGroup
import java.util.*

object TransitionUtils {

    fun setAncestralClipping(view: View, clipChildren: Boolean): List<Boolean> {
        return setAncestralClipping(view, clipChildren, ArrayList())
    }

    private fun setAncestralClipping(
            view: View, clipChildren: Boolean, was: MutableList<Boolean>): List<Boolean> {
        if (view is ViewGroup) {
            was.add(view.clipChildren)
            view.clipChildren = clipChildren
        }
        val parent = view.parent
        if (parent != null && parent is ViewGroup) {
            setAncestralClipping(parent, clipChildren, was)
        }
        return was
    }

    fun restoreAncestralClipping(view: View, was: MutableList<Boolean>) {
        if (view is ViewGroup) {
            view.clipChildren = was.removeAt(0)
        }
        val parent = view.parent
        if (parent != null && parent is ViewGroup) {
            restoreAncestralClipping(parent, was)
        }
    }
}

