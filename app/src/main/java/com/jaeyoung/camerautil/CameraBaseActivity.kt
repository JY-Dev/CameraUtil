package com.jaeyoung.camerautil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
open class CameraBaseActivity : AppCompatActivity() {
    @Inject
    lateinit var cameraUtil: CameraUtil
    val cameraViewModel by viewModels<CameraViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) return
        when (requestCode) {
            cameraUtil.REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK)
                    getImage(CameraStatus.ALBUM.name, data?.data)
            }
            cameraUtil.REQUEST_TAKE_ALBUM -> {
                if (resultCode == Activity.RESULT_OK)
                    getImage(CameraStatus.ALBUM.name, data?.data)
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getImage(where: String, uri: Uri?) {
        when (where) {
            CameraStatus.ALBUM.name -> cameraUtil.setImageUri(uri)
            CameraStatus.CAMERA.name -> cameraUtil.galleryAddPic()
        }
        cameraViewModel.setImageUri(cameraUtil.getImageUri())
    }

    fun gotoAlbum() = cameraUtil.getAlbum()
    fun gotoCamera() = cameraUtil.captureCamera()
}