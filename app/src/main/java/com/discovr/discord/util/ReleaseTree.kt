package com.discovr.discord.util

import android.util.Log

import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        if (throwable != null) {
            if (priority == Log.ERROR) {
                // Log report
            } else if (priority == Log.WARN) {
                // Log report
            }
        }
    }
}