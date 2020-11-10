package com.jaeyoung.camerautil

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CameraViewModel @ViewModelInject constructor() : ViewModel(){
    val imageUri : LiveData<Uri?> get() = data
    private val  data = MutableLiveData<Uri?>()
    fun setImageUri(uri: Uri?) = data.postValue(uri)
}