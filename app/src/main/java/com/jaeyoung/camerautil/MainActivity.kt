package com.jaeyoung.camerautil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.test_layout.view.*
import java.util.zip.Inflater
import javax.inject.Inject

class MainActivity : CameraBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraViewModel.imageUri.observe(this, Observer {
            test_img.setImageURI(it)
        })
        test1.setOnClickListener {
            gotoAlbum()
        }
        test2.setOnClickListener {
            gotoCamera()
        }

    }
}