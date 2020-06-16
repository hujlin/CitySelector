package com.example.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.test.utils.dip2px
import com.example.test.utils.sp2px
import kotlin.math.max

/**
 *@author hujl
 *      Email: hujlin@163.com
 *      Date : 2020-06-15 17:19
 */
class CityItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var mGroupHeight = dip2px(context, 40f)
    private var mLeftMargin = dip2px(context, 12f)

    private val mGroupPaint = Paint().apply {
        this.color = Color.parseColor("#EAEAEA")

    }
    private val mTextPaint = Paint().apply {
        textSize = sp2px(context, 14f)
        color = Color.parseColor("#3c3c3c")
        isAntiAlias = true
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val adapter = parent.adapter as CityAdapter
        val data = adapter.data
        val itemCount = state.itemCount
        val childCount = parent.childCount
        val left = parent.left + parent.paddingStart
        val right = parent.right - parent.paddingEnd
        var preGroupName: String
        var currentGroupName = ""
        for (i in (0 until childCount)) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            preGroupName = currentGroupName
            currentGroupName = data[position].character
            if (currentGroupName.isEmpty() || currentGroupName == preGroupName) continue
            val viewBottom = view.bottom
            var top = max(mGroupHeight, view.top)
            if (position + 1 < itemCount) {
                val nextGroupName = data[position + 1].character
                if (currentGroupName != nextGroupName && viewBottom < top) {
                    top = viewBottom
                }
            }
            c.drawRect(left.toFloat(), (top - mGroupHeight).toFloat(), right.toFloat(), top.toFloat(), mGroupPaint)
            val fm = mTextPaint.fontMetrics
            val baseLine = top - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom
            c.drawText(currentGroupName, (left + mLeftMargin).toFloat(), baseLine, mTextPaint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter as CityAdapter
        val data = adapter.data
        val pos = parent.getChildAdapterPosition(view)
        if (pos == 0 || isFirstInGroup(data, pos)) {
            outRect.top = mGroupHeight
        }
    }

    private fun isFirstInGroup(data: List<City>, pos: Int): Boolean {
        if (pos == 0) return true
        return !TextUtils.equals(data[pos - 1].character, data[pos].character)
    }


}