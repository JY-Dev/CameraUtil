package com.jaeyoung.camerautil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var cameraUtil : CameraUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraUtil = CameraUtil(this)
        test1.setOnClickListener {
            cameraUtil.getAlbum()
        }
        test2.setOnClickListener {
            cameraUtil.captureCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != RESULT_OK) return
        when(requestCode){
            cameraUtil.REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        cameraUtil.galleryAddPic()
                        Toast.makeText(this,"Test1",Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                    }

                } else {
                    Toast.makeText(this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            cameraUtil.REQUEST_TAKE_ALBUM ->{
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.data != null) {
                        try {
                            cameraUtil.setImageUri(data.data)

                            //cropImage()
                        } catch (e: Exception) {
                        }
                    }
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}