package com.app.fitpeo_assignment.network.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.fitpeo_assignment.network.interfaces.PhotoService
import com.app.fitpeo_assignment.network.model.PhotoData
import com.app.fitpeo_assignment.network.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotoViewModelTest {

    @Mock
    private lateinit var photoService: PhotoService

    private lateinit var photoRepository: PhotoRepository
    private lateinit var viewModel: PhotoViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        photoRepository = PhotoRepository(photoService)
        viewModel = PhotoViewModel(photoRepository)

        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchPhotos_success() = runTest {
        val expectedPhotos = listOf(
            PhotoData(
                1,
                1,
                "Title 1",
                "http://test.com/photo1.jpg",
                "http://test.com/thumbnail1.jpg"
            ),
            PhotoData(
                1,
                2,
                "Title 2",
                "http://test.com/photo2.jpg",
                "http://test.com/thumbnail2.jpg"
            )
        )
        `when`(photoService.getPhotos()).thenReturn(expectedPhotos)

        viewModel.fetchPhotos()

        val photos = viewModel.photos.value
        assertEquals(expectedPhotos, photos)
    }

    @Test
    fun fetchPhotos_error() = runTest {
        val errorMessage = "Error fetching photos"
        `when`(photoService.getPhotos()).thenThrow(RuntimeException(errorMessage))

        viewModel.fetchPhotos()

        val error = viewModel.error
        assertEquals(errorMessage, error)
    }
}

