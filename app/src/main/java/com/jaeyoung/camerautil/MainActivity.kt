package com.jaeyoung.camerautil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.test_layout.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    lateinit var cameraUtil : CameraUtil
    var imagArray = mutableListOf<Uri?>()

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
                        imageAdd(cameraUtil.getImageUri())

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
                            imageAdd(cameraUtil.getImageUri())
                            //cropImage()
                        } catch (e: Exception) {
                        }
                    }
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun imageAdd(uri:Uri?) {
        val view = layoutInflater.inflate(R.layout.test_layout, null) as LinearLayout
        imagArray.add(uri)
        view.test_Img.setImageURI(uri)
        view.test_btn.setOnClickListener {
            imagArray.remove(uri)
            test_view.removeView(view)
        }
        test_view.addView(view)
    }
}