package com.ruzhan.common.util

import android.text.TextUtils
import android.util.Log

object LogUtils {

    private val mTag = LogUtils::class.java.simpleName
    var isDebug: Boolean = false

    init {
        isDebug = ConstantUtils.isDebugBuild()
    }

    //for error log
    @JvmStatic
    fun error(msg: String) {
        if (isDebug) {
            Log.e(mTag, msg)
        }
    }

    //for warming log
    @JvmStatic
    fun warn(msg: String) {
        if (isDebug) {
            Log.w(mTag, msg)
        }
    }

    //for info log
    @JvmStatic
    fun info(msg: String) {
        if (isDebug) {
            Log.i(mTag, msg)
        }
    }

    //for debug log
    @JvmStatic
    fun debug(msg: String) {
        if (isDebug) {
            Log.d(mTag, msg)
        }
    }

    //for verbose log
    @JvmStatic
    fun verbose(msg: String) {
        if (isDebug) {
            Log.v(mTag, msg)
        }
    }

    //for error log
    @JvmStatic
    fun e(msg: String) {
        if (isDebug) {
            Log.e(mTag, msg)
        }
    }

    //for warming log
    @JvmStatic
    fun w(msg: String) {
        if (isDebug) {
            Log.w(mTag, msg)
        }
    }

    //for info log
    @JvmStatic
    fun i(msg: String) {
        if (isDebug) {
            Log.i(mTag, msg)
        }
    }

    //for debug log
    @JvmStatic
    fun d(msg: String) {
        if (isDebug) {
            Log.d(mTag, msg)
        }
    }

    //for verbose log
    @JvmStatic
    fun v(msg: String) {
        if (isDebug) {
            Log.v(mTag, msg)
        }
    }


    //for warming log
    @JvmStatic
    fun w(tag: String?, msg: String) {
        var tag = tag
        if (isDebug) {
            if (tag == null || "".equals(tag.trim { it <= ' ' }, ignoreCase = true)) {
                tag = mTag
            }
            Log.w(tag, msg)
        }
    }

    //for info log
    @JvmStatic
    fun i(tag: String?, msg: String) {
        var tag = tag
        if (isDebug) {
            if (tag == null || "".equals(tag.trim { it <= ' ' }, ignoreCase = true)) {
                tag = mTag
            }
            Log.i(tag, msg)
        }
    }

    //for debug log
    @JvmStatic
    fun d(tag: String?, msg: String) {
        var tag = tag
        if (isDebug) {
            if (tag == null || "".equals(tag.trim { it <= ' ' }, ignoreCase = true)) {
                tag = mTag
            }
            Log.d(tag, msg)
        }
    }

    //for verbose log
    @JvmStatic
    fun v(tag: String?, msg: String) {
        var tag = tag
        if (isDebug) {
            if (tag == null || "".equals(tag.trim { it <= ' ' }, ignoreCase = true)) {
                tag = mTag
            }
            Log.v(tag, msg)
        }
    }

    //for verbose log
    @JvmStatic
    fun e(tag: String?, msg: String) {
        var tag = tag
        if (isDebug) {
            if (tag == null || "".equals(tag.trim { it <= ' ' }, ignoreCase = true)) {
                tag = mTag
            }
            Log.e(tag, msg)
        }
    }

    /**
     * 点击Log跳转到指定源码位置
     *
     * @param tag
     * @param msg
     */
    @JvmStatic
    fun showLog(tag: String, msg: String) {
        var tag = tag
        if (isDebug && !TextUtils.isEmpty(msg)) {
            if (TextUtils.isEmpty(tag)) tag = mTag
            val stackTraceElement = Thread.currentThread().stackTrace
            var currentIndex = -1
            for (i in stackTraceElement.indices) {
                if (stackTraceElement[i].methodName.compareTo("showLog") == 0) {
                    currentIndex = i + 1
                    break
                }
            }
            if (currentIndex >= 0) {
                val fullClassName = stackTraceElement[currentIndex].className
                val className = fullClassName.substring(fullClassName
                        .lastIndexOf(".") + 1)
                val methodName = stackTraceElement[currentIndex].methodName
                val lineNumber = stackTraceElement[currentIndex].lineNumber.toString()

                Log.i(tag, msg + "\n  ---->at " + className + "." + methodName + "("
                        + className + ".java:" + lineNumber + ")")
            } else {
                Log.i(tag, msg)
            }
        }
    }
}
