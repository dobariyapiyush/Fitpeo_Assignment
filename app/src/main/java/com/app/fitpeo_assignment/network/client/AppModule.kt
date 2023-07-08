package com.app.fitpeo_assignment.network.client

import com.app.fitpeo_assignment.activity.MainActivity
import com.app.fitpeo_assignment.network.core.BASE_URL
import com.app.fitpeo_assignment.network.interfaces.PhotoService
import com.app.fitpeo_assignment.network.repository.PhotoRepository
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {
    @Provides
    fun providePhotoApiService(): PhotoService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
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
    fun providePhotoViewModelFactory(): PhotoViewModelFactory
}

