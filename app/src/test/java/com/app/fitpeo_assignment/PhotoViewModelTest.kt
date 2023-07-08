package com.app.fitpeo_assignment

import com.app.fitpeo_assignment.network.model.PhotoData
import com.app.fitpeo_assignment.network.repository.PhotoRepository
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(MockitoJUnitRunner::class)
class PhotoViewModelTest {
    @Mock
    private lateinit var photoRepository: PhotoRepository

    private lateinit var viewModel: PhotoViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = PhotoViewModel(photoRepository)
    }

    @Test
    fun `fetchPhotos should update photos LiveData`() = runBlocking<Unit> {
        val expectedPhotos = listOf(
            PhotoData(
                1,
                1,
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
            )
        )
        `when`(photoRepository.getPhotos()).thenReturn(expectedPhotos)

        viewModel.fetchPhotos()

        val photos = viewModel.photos.value
        assertThat(photos).isEqualTo(expectedPhotos)
    }
}
