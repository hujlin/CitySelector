package com.example.test.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 *@author hujl
 *      Email: hujlin@163.com
 *      Date : 2020-06-16 11:22
 */

fun dip2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun sp2px(context: Context, spValue: Float): Float {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f)
}

fun hideSoftInput(view: View): Boolean {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.hideSoftInputFromWindow(view.windowToken, 0)
}