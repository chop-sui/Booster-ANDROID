package com.example.booster

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View.GONE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("changeCircleF")
fun ImageView.changeCircleF(status : Int) {
    if (status==1){
        setImageResource(R.drawable.process_ic_receipt_1)
    }else{
        setImageResource(R.drawable.process_ic_doing_1)
    }
}

@BindingAdapter("changeCircleS")
fun ImageView.changeCircleS(status : Int) {
    if (status==1){
        setImageResource(R.drawable.process_ic_receipt_2)
    } else if(status==2){
        setImageResource(R.drawable.process_ic_doing_2)
    } else {
        setImageResource(R.drawable.process_ic_done_2)
    }
}

@BindingAdapter("changeCircleT")
fun ImageView.changeCircleT(status : Int) {
    if (status==1 || status==2){
        setImageResource(R.drawable.process_ic_receipt_2)
    }else{
        setImageResource(R.drawable.process_ic_done_3)
    }
}

@BindingAdapter("setGradation")
fun LinearLayout.setGradation(status : Int) {
    if (status==1){
        setBackgroundResource(R.drawable.bg_progress_receipt)
    }else if(status==2){
        setBackgroundResource(R.drawable.bg_progress_ing)
    }else{
        setBackgroundResource(R.drawable.bg_progress_done)
    }
}

@BindingAdapter("setPickUpBtn")
fun TextView.setPickUpBtn(status : Int) {
    if (status==3){
        setBackgroundResource(R.drawable.bg_progress_ready_to_pick)
    }else{
        setBackgroundResource(R.drawable.bg_dddddd_round)
    }
}

@BindingAdapter("setCancelVisible")
fun TextView.setCancelVisible(status : Int) {
    if (status!=1){
        visibility = GONE
    }
}

@BindingAdapter("setSubwayCircle")
fun LinearLayout.setSubwayCircle(subway : Int) {
    Log.e("setSub", subway.toString())
    if (subway==2){
        setBackgroundResource(R.drawable.bg_subway_circle)
        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3db449"))
    }
}