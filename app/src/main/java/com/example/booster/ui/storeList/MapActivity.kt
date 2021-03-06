package com.example.booster.ui.storeList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.UiThread
import androidx.fragment.app.FragmentActivity
import com.example.booster.R
import com.example.booster.data.datasource.model.MarkerData
import com.example.booster.listener.onlyOneClickListener
import com.example.booster.ui.storeDetail.StoreDetailActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker

import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : FragmentActivity(), OnMapReadyCallback {
    var markers = ArrayList<MarkerData>()
    var array = mutableListOf<Marker>()
    var university = ""
    lateinit var cameraUpdate: CameraUpdate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        markers = intent.getParcelableArrayListExtra<MarkerData>("marker")!!
        university = intent.getStringExtra("univ")!!

        setContentView(R.layout.activity_map)

        btn_back.onlyOneClickListener {
            markers.clear()
            array.clear()
            finish()
        }

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mainMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mainMap, it).commit()
            }

        mapFragment.getMapAsync(this)

    }

    @UiThread
    override fun onMapReady(nMap: NaverMap) {

        val uiSettings = nMap.uiSettings

        uiSettings.isScaleBarEnabled = false

        if (university == "숭실대학교"){
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.496575, 126.957427))
        }
        else if (university == "중앙대학교"){
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.505233, 126.957112))
        }
        else{//서울대학교
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.481431, 126.952701))
        }
        act_map_txt_univ.text = university

        nMap.moveCamera(cameraUpdate)
        draw(nMap)

    }

    fun draw(nMap: NaverMap){
        for(i in 0 until markers.size){
            Log.e("marker", markers[i].toString())
            repeat(1000) {
                array.plusAssign(Marker().apply {
                    position = LatLng(markers[i].latitude!!.toDouble(), markers[i].longitude!!.toDouble())
                    icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker)
                    tag = markers[i].name
                    width = Marker.SIZE_AUTO
                    height = Marker.SIZE_AUTO
                })
            }
        }

        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?:""
            }
        }

        array.forEach { marker ->
            marker.map = nMap

            marker.setOnClickListener {

                for ( i in 0 until array.size){
                    array[i].icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker)
                }
                marker.icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker_click)

                val cameraUpdate = CameraUpdate.scrollTo(marker.position)
                nMap.moveCamera(cameraUpdate)

                infoWindow.open(marker)
                infoWindow.setOnClickListener {
                    val intent = Intent(this, StoreDetailActivity::class.java)
                    for(i in 0 .. array.size){
                        if(markers[i].name == marker.tag){
                            val idx = markers[i].idx
                            intent.putExtra("storeIdx", idx)
                            break
                        }
                    }
                    startActivity(intent)
                    finish()
                    false

                }

                false
            }


        }
    }
}