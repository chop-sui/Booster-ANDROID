package com.example.booster.bindingadapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.booster.R
import kotlinx.android.synthetic.main.my_file.view.*

@BindingAdapter("changeCircleF")
fun ImageView.changeCircleF(status : Int) {
    if (status==1){
        setImageResource(R.drawable.process_ic_receipt_1)
    }else if(status==2 || status==3){
        setImageResource(R.drawable.process_ic_doing_1)
    }
}

@BindingAdapter("changeCircleS")
fun ImageView.changeCircleS(status : Int) {
    if (status==1){
        setImageResource(R.drawable.process_ic_receipt_2)
    } else if(status==2){
        setImageResource(R.drawable.process_ic_doing_2)
    } else if(status==3){
        setImageResource(R.drawable.process_ic_done_2)
    }
}

@BindingAdapter("changeCircleT")
fun ImageView.changeCircleT(status : Int) {
    if (status==1 || status==2){
        setImageResource(R.drawable.process_ic_receipt_2)
    }else if(status==3){
        setImageResource(R.drawable.process_ic_done_3)
    }
}

@BindingAdapter("setGradation")
fun View.setGradation(status : Int) {
    if (status==1){
        setBackgroundResource(R.drawable.bg_progress_receipt)
    }else if(status==2){
        setBackgroundResource(R.drawable.bg_progress_ing)
    }else if(status==3){
        setBackgroundResource(R.drawable.bg_progress_done)
    }
}

@BindingAdapter("setPickUpBtn")
fun TextView.setPickUpBtn(status : Int) {
    if (status==3){
        setBackgroundResource(R.drawable.bg_progress_ready_to_pick)
    }else {
        setBackgroundResource(R.drawable.bg_dddddd_round)
    }
}

@BindingAdapter("setCancelVisible")
fun TextView.setCancelVisible(status : Int) {
    if (status==2 || status==3){
        visibility = GONE
    }
}

@BindingAdapter("setSubwayCircle")
fun LinearLayout.setSubwayCircle(subway : Int) {
    if (subway==2){
        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3db449"))
    } else if (subway==7){
        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8a8623"))
    } else if (subway==9){
        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#cda43a"))
    }
}

@BindingAdapter("setClosedText")
fun TextView.setClosedText(close : String?) {
    if (close == "휴무"){
        setTextColor(Color.parseColor("#ff1200"))
    }
    text = close
}

@BindingAdapter("setFavStar")
fun ImageView.setFavStar(status : Int) {
    if (status==0){
//        setImageResource(android.R.color.transparent)
        setImageResource(R.drawable.store_detail_ic_star_inactive)
    }else if (status==1){
//        setImageResource(android.R.color.transparent)
        setImageResource(R.drawable.store_detail_ic_star_active)
    }
}

@BindingAdapter("setFavStarList")
fun ImageView.setFavStarList(status : Int) {
    if (status==0){
        setImageResource(R.drawable.store_ic_inactive_star)
    }else{
        setImageResource(R.drawable.store_ic_active_star)
    }
}

@BindingAdapter("setStoreImg")
fun setStoreImg(view: ImageView, url : String?) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("setStoreBlackImg")
fun setStoreBlackImg(view: ImageView, open : Int?) {
    if(open==0) {
        view.visibility = VISIBLE
        view.alpha = 0.7f
    }else{
        view.visibility = INVISIBLE
    }
}

@BindingAdapter("setStoreCloseTxt")
fun setStoreCloseTxt(view: TextView, open : Int?) {
    if(open==0) {
        view.visibility = VISIBLE
    }else{
        view.visibility = INVISIBLE
    }
}

@BindingAdapter("setBtnActive")
fun TextView.setBtnActive(close : Int?) {
    if (close == 0){
        setBackgroundResource(R.drawable.bg_dddddd_round)
    }else{
        setBackgroundResource(R.drawable.bg_btn_gradation)
    }
}

@BindingAdapter("setFileName")
fun TextView.setFileName(name: String?){
    Log.e("setFileName -----> ", name)
    setFileName(name!!.substring(0, name.length-4))
}

