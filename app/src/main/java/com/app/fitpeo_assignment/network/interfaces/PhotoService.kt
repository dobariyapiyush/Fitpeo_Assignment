package com.app.fitpeo_assignment.network.interfaces

import com.app.fitpeo_assignment.network.core.PARAMETER_PHOTOS
import com.app.fitpeo_assignment.network.model.PhotoData
import retrofit2.http.GET

interface PhotoService {
    @GET(PARAMETER_PHOTOS)
    suspend fun getPhotos(): List<PhotoData>
}
