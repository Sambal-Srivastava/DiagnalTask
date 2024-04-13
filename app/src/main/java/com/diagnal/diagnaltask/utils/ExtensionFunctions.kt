package com.diagnal.diagnaltask.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.diagnal.diagnaltask.BuildConfig

fun Context.toast(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    toast.show()
}

fun Any.logE(tag: String, message: String?) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, if (message.isNullOrEmpty()) "NULL" else message)
    }
}

fun View.hideKeyboard(activity: Activity) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard(editText: EditText, isForced: Boolean) {
    val inputMethodManager =
        editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (isForced) {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        editText.requestFocus()
    } else {
        @Suppress("DEPRECATION")
        inputMethodManager.showSoftInputFromInputMethod(editText.windowToken, 0)
    }
}
