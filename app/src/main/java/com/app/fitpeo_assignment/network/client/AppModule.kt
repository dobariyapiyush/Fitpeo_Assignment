package com.app.fitpeo_assignment.network.client

import com.app.fitpeo_assignment.ui.activity.MainActivity
import com.app.fitpeo_assignment.network.core.BASE_URL
import com.app.fitpeo_assignment.network.interfaces.PhotoService
import com.app.fitpeo_assignment.network.repository.PhotoRepository
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import android.util.Base64
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

@Module
class AppModule {

    @Provides
    fun providePhotoApiService(): PhotoService {
        val retrofit = Retrofit.Builder()
            .baseUrl(decodeUrl(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PhotoService::class.java)
    }

    @Provides
    fun providePhotoRepository(apiService: PhotoService): PhotoRepository {
        return PhotoRepository(apiService)
    }

    @Provides
    fun providePhotoViewModelFactory(repository: PhotoRepository): PhotoViewModelFactory {
        return PhotoViewModelFactory(repository)
    }
}

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun getPhotoViewModelFactory(): PhotoViewModelFactory
}

fun decodeUrl(url: String): String {
    val decodedBytes: ByteArray = Base64.decode(url, Base64.DEFAULT)
    return String(decodedBytes, StandardCharsets.UTF_8)
}
