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
                    getImage(data?.data)
            }
            cameraUtil.REQUEST_TAKE_ALBUM -> {
                if (resultCode == Activity.RESULT_OK)
                    getImage(data?.data)
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getImage( uri: Uri?) {
        cameraUtil.setImageUri(uri)
        cameraViewModel.setImageUri(cameraUtil.getImageUri())
    }

    fun gotoAlbum() = cameraUtil.getAlbum()
    fun gotoCamera() = cameraUtil.captureCamera()
}