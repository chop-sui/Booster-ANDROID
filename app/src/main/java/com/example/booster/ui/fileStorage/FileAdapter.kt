package com.example.booster.ui.fileStorage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booster.R
import com.example.booster.data.datasource.model.FileData
import com.example.booster.util.BoosterUtil
import kotlinx.android.synthetic.main.my_file.view.*
import java.io.File

class FileAdapter(var datas: ArrayList<com.example.booster.data.datasource.model.FileData>):
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.my_file, parent, false)
        return ViewHolder(item)
    }


    override fun getItemCount(): Int {
        return datas.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(file: com.example.booster.data.datasource.model.FileData) {
            //Log.e("uri", file.uri.path.toString())

            Log.e("file", file.name + " " + file.type)
            if(file.type=="img"){
                Glide.with(itemView.context).load(file.uri).into(itemView.iv_file)
            } else {
                val fileImage = BoosterUtil(itemView.context).getFileImage(file.type)
                Glide.with(itemView.context).load(fileImage).into(itemView.iv_file)
            }

            itemView.tv_file_name.text = file.name


        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }


}