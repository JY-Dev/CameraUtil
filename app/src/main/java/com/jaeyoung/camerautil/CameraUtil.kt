package com.jaeyoung.camerautil

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class CameraUtil @Inject constructor(@ActivityContext private val context: Context) {
    val REQUEST_TAKE_PHOTO = 2222
    val REQUEST_TAKE_ALBUM = 3333
    private val mContext = context

    //private val REQUEST_IMAGE_CROP = 4444
    private lateinit var mCurrentPhotoPath: String
    private var imageUri: Uri? = null
    private val activity = mContext as Activity
    lateinit var f: File

    private fun permission(func: () -> Unit) {
        val permissionListener = object : PermissionListener {
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

            }

            override fun onPermissionGranted() {
                func()
            }
        }

        TedPermission.with(mContext)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(mContext.resources.getString(R.string.permission_2))
            .setDeniedMessage(mContext.resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()
    }

    fun galleryAddPic() {
        permission {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
            f = File(mCurrentPhotoPath)
            val contentUri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            activity.sendBroadcast(mediaScanIntent)
            Toast.makeText(mContext, "사진 등록 완료", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAlbum() {
        permission {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = MediaStore.Images.Media.CONTENT_TYPE
                setDataAndType(imageUri, "image/*")
            }
            activity.startActivityForResult(intent, REQUEST_TAKE_ALBUM)
        }
    }

    fun captureCamera() {
        permission {
            val state = Environment.getExternalStorageState()
            // 외장 메모리 검사
            if (Environment.MEDIA_MOUNTED == state) {
                val providerURI = FileProvider.getUriForFile(mContext, mContext.packageName, createImageFile())
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            } else {
                Toast.makeText(mContext, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            mCurrentPhotoPath = absolutePath
        }
    }

    fun getImageUri(): Uri? {
        return imageUri
    }

    fun setImageUri(uri: Uri?) {
        imageUri = uri
    }
}