package com.rizal.nontonmovieyuk.genre

import android.os.Looper
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rizal.nontonmovieyuk.data.model.Genres
import com.rizal.nontonmovieyuk.data.model.GenresItem
import com.rizal.nontonmovieyuk.data.repository.Repository
import com.rizal.nontonmovieyuk.ui.genres.GenresViewModel
import com.rizal.nontonmovieyuk.utils.network.Results
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


//@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class GenresViewModelTest {

    private lateinit var viewModel: GenresViewModel
    private lateinit var repository: Repository

    @MockK
    private lateinit var genresLiveDataObserver: Observer<Genres>

    @MockK
    private lateinit var onLoadErrorLiveDataObserver: Observer<Boolean>

    @Before
    fun setUp() { // memberikan mock response untuk Looper.getMainLooper()
        mockkStatic(Looper::class)
        every { Looper.getMainLooper() } returns mockk(relaxed = true)

        // memberikan mock response untuk ArchTaskExecutor.isMainThread()
        mockkStatic(ArchTaskExecutor::class)
        every { ArchTaskExecutor.getInstance().isMainThread } returns true
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = mockk()
        viewModel = GenresViewModel(repository)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `fetchGenres success`() {
        // given
        val genres = Genres(listOf(GenresItem(28,"Action")))
        coEvery { repository.requestGenres() } returns Results.Success(genres)

        viewModel.genres.observeForever(genresLiveDataObserver)
        viewModel.onLoadError.observeForever(onLoadErrorLiveDataObserver)

        // when
        viewModel.fetchGenres()

        // then
        verifyOrder {
            genresLiveDataObserver.onChanged(genres)
            onLoadErrorLiveDataObserver.onChanged(false)
        }
    }
}