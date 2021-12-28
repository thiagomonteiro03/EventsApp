package com.example.eventssicredi.ui.eventlistfragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.eventssicredi.R
import com.example.eventssicredi.model.EventEntity
import com.example.eventssicredi.service.RepositoryInterface
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class EventListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var eventsObserver: Observer<List<EventEntity>>
    @Mock
    private lateinit var errorObserver: Observer<Int>

    private lateinit var viewModel: EventListViewModel

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `when view model loadEvents get success then sets events`(){
        // Arrange
        val events = listOf(
            EventEntity(2, "Honda", "City", 3.0, 2.4, 1.9 ,"title" ,"1")
        )

        val repository = MockRepository(Response.success(events))
        viewModel = EventListViewModel(repository)
        viewModel.events.observeForever(eventsObserver)

        // Act
        runBlocking {
            viewModel.handlingEvents()
        }

        // Assert
        verify(eventsObserver).onChanged(events)
    }

    @Test
    fun `when view model loadEvents get error 401 then sets true for error liveData`(){
        // Arrange
        val responseBody: ResponseBody = Mockito.mock(ResponseBody::class.java)
        Mockito.`when`(responseBody.source()).thenThrow(RuntimeException())
        val resultServerError = MockRepository(Response.error(401, responseBody))
        viewModel = EventListViewModel(resultServerError)
        viewModel.errorMessage.observeForever(errorObserver)

        // Act
        runBlocking {
            viewModel.handlingEvents()
        }

        // Assert
        verify(errorObserver).onChanged(R.string.connection_error_401)
    }
}

class MockRepository(private val response: Response<List<EventEntity?>>) : RepositoryInterface {

    override suspend fun getApiData(): Response<List<EventEntity?>> {
        return response
    }

}