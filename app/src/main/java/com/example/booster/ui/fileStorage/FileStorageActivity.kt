package com.example.booster.ui.fileStorage

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.FileData
import com.example.booster.ui.StoreFileOptionActivity
import com.example.booster.util.BoosterUtil
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_DOCS
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_MEDIA
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_DOC
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO
import kotlinx.android.synthetic.main.activity_file_storage.*
import kotlinx.android.synthetic.main.dialog_item_view.*


class FileStorageActivity : AppCompatActivity() {

    private val CUSTOM_REQUEST_CODE: Int = 532

    var datas:ArrayList<FileData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_storage)

        fileStorage_rv_file_add.apply {
            layoutManager = LinearLayoutManager(this@FileStorageActivity)
            adapter = FileAdapter(datas, {item, position -> itemDelete(item, position)}, {item, position -> itemOptionChange(item, position)},
                {item, position -> itemOptionView(item, position)})
        }
        fileStorage_rv_file_add.addItemDecoration(
            MarginItemDecoration(
            resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault),
            resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault)
        )
        )
            fileStorage_tv_order.visibility = View.GONE
            fileStorage_tv_cost.visibility = View.GONE
            fileStorage_tv_cost_amount.visibility = View.GONE

    }

    private fun itemOptionChange(item: FileData, position:Int) {
        val intent = Intent(this@FileStorageActivity, StoreFileOptionActivity::class.java)
        startActivity(intent)
    }

    private fun itemOptionView(item: FileData, position:Int) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_item_view, null)
        val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            .create()
        val dialogclose = view.findViewById<ImageView>(R.id.dial_item_view_close)
        dialogclose.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    private fun itemDelete(item: FileData, position:Int) {
        val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        val dialogView = layoutInflater.inflate(R.layout.dialog_item_delete, null)
        val textView: TextView = dialogView.findViewById(R.id.dial_item_delete_tv_message)
        textView.text = item.name + "를 삭제하시겠습니까?"
        builder.setView(dialogView)
            .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                datas.remove(item)
                fileStorage_rv_file_add.adapter?.notifyItemRemoved(position)
                if (datas.size == 0) {
                    fileStorage_tv_order.visibility = View.GONE
                    fileStorage_tv_cost.visibility = View.GONE
                    fileStorage_tv_cost_amount.visibility = View.GONE
                }
            }
            .setNegativeButton("아니오") { dialog: DialogInterface?, which: Int ->

            }
            .show()

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        var photoPaths:ArrayList<Uri> = ArrayList()
        var docPaths:ArrayList<Uri> = ArrayList()
        when (requestCode) {
            REQUEST_CODE_PHOTO -> if (resultCode === Activity.RESULT_OK && data != null) {

                photoPaths.addAll(data.getParcelableArrayListExtra<Uri>(KEY_SELECTED_MEDIA))
            }
            REQUEST_CODE_DOC -> if (resultCode === Activity.RESULT_OK && data != null) {

                docPaths.addAll(data.getParcelableArrayListExtra<Uri>(KEY_SELECTED_DOCS))
            }
        }
        addThemToView(photoPaths, docPaths)

    }

    private fun addThemToView(
        imagePaths: ArrayList<Uri>?,
        docPaths: ArrayList<Uri>?
    ) {
        val filePaths: ArrayList<Uri> = ArrayList()
        if (imagePaths != null) {
            for (imguri in imagePaths) {
                val file: FileData = FileData(imguri)
                file.name = BoosterUtil(this).getFileName(imguri)
                file.type = "img"
                datas.add(file)
            }
        }
        if (docPaths != null) {
            for (docuri in docPaths) {
                val file:FileData = FileData(docuri)
                file.name = BoosterUtil(this).getFileName(docuri)
                file.type = BoosterUtil(this).getFileType(docuri)
                datas.add(file)
            }
        }

        if (datas.size >= 1) {
            fileStorage_tv_order.visibility = View.VISIBLE
            fileStorage_tv_cost.visibility = View.VISIBLE
            fileStorage_tv_cost_amount.visibility = View.VISIBLE
        } else {
            fileStorage_tv_order.visibility = View.GONE
            fileStorage_tv_cost.visibility = View.GONE
            fileStorage_tv_cost_amount.visibility = View.GONE
        }

        fileStorage_rv_file_add.adapter?.notifyDataSetChanged()


//        if (recyclerview != null) {
//            val layoutManager =
//                StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
//            layoutManager.gapStrategy =
//                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
//            recyclerView.layoutManager = layoutManager
//            val imageAdapter =
//                ImageAdapter(this, filePaths, object : ImageAdapterListener() {
//                    fun onItemClick(uri: Uri?) {
//                        try {
//                            //make sure to use this getFilePath method from worker thread
//                            val path = getFilePath(recyclerView.context, uri!!)
//                            if (path != null) {
//                                Toast.makeText(recyclerView.context, path, Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                        } catch (e: URISyntaxException) {
//                            e.printStackTrace()
//                        }
//                    }
//                })
//            recyclerView.adapter = imageAdapter
//            recyclerView.itemAnimator = DefaultItemAnimator()
//        }
        Toast.makeText(this, "Num of files selected: " + filePaths.size, Toast.LENGTH_SHORT)
            .show()
    }

    fun onClick(view: View) {
        when(view) {
            fileStorage_img_close -> showDeleteDialog()
            fileStorage_iv_file_add -> fileAdd()
        }
    }


    private fun fileAdd() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle2)
        builder.setTitle("추가할 파일의 종류를 선택해주세요")
        builder.setPositiveButton("이미지") { dialogInterface: DialogInterface, i: Int ->
            FilePickerBuilder.instance
                .setActivityTheme(R.style.LibAppTheme) //optional
                .setActivityTitle("이미지 선택")

                .pickPhoto(this, REQUEST_CODE_PHOTO);
        }
        builder.setNegativeButton("문서") { dialogInterface: DialogInterface, i: Int ->
            FilePickerBuilder.instance
                .setActivityTheme(R.style.LibAppTheme) //optional
                .setActivityTitle("문서 선택")
                .pickFile(this, REQUEST_CODE_DOC);
        }
        builder.show()

    }


    private fun showDeleteDialog() {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            val dialogView = layoutInflater.inflate(R.layout.dialog_return, null)


            builder.setView(dialogView)
                .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                    setResult(RESULT_OK)
                    finish()
                }
                .setNegativeButton("아니오") { dialog: DialogInterface?, which: Int ->

                }
                .show()
    }

}