package com.htueko.apod.util

import timber.log.Timber

object Logger {
    fun debug(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    fun error(tag: String, message: String) {
        Timber.tag(tag).e(message)
    }

    fun info(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }

    fun warn(tag: String, message: String) {
        Timber.tag(tag).w(message)
    }

    fun wtf(tag: String, message: String) {
        Timber.tag(tag).wtf(message)
    }

    fun logCoroutineInfo(tag: String, msg: String) {
        Timber.tag(tag).d("Running on: ${Thread.currentThread().name} with message: $msg")
    }

}