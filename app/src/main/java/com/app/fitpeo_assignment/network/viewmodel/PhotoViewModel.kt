package com.app.fitpeo_assignment.network.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.fitpeo_assignment.network.model.PhotoData
import com.app.fitpeo_assignment.network.repository.PhotoRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotoViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    private val _photos = MutableLiveData<List<PhotoData>>()
    val photos: LiveData<List<PhotoData>> = _photos
    var error: String = ""

    fun fetchPhotos() {
        viewModelScope.launch {
            try {
                val result = photoRepository.getPhotos()
                _photos.value = result
            } catch (e: Exception) {
                error = e.message.toString()
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PhotoViewModelFactory(private val repository: PhotoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

