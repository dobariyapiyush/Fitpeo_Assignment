package com.app.fitpeo_assignment.network.repository

import com.app.fitpeo_assignment.network.interfaces.PhotoService
import com.app.fitpeo_assignment.network.model.PhotoData

open class PhotoRepository(private val apiService: PhotoService) {
    suspend fun getPhotos(): List<PhotoData> {
        return apiService.getPhotos()
    }
}
