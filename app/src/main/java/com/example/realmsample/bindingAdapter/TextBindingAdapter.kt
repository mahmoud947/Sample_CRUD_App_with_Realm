package com.example.realmsample.bindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setId")
fun bindId(textView: TextView,value: Long){
    textView.text = value.toString()
}