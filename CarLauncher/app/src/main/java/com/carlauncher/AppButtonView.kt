package com.carlauncher

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class AppButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val tvIcon: TextView
    private val tvName: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_app_button, this, true)
        tvIcon = findViewById(R.id.tv_app_icon)
        tvName = findViewById(R.id.tv_app_name)
        orientation = VERTICAL
        gravity = android.view.Gravity.CENTER
        setBackgroundResource(R.drawable.btn_app_bg)
    }

    fun setAppInfo(icon: String, label: String) {
        tvIcon.text = icon
        tvName.text = label
    }
}
