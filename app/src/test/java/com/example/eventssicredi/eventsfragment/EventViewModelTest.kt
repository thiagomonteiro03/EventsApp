package com.example.eventssicredi.eventsfragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.eventssicredi.R
import com.example.eventssicredi.model.Checkin
import com.example.eventssicredi.model.EventEntity
import com.example.eventssicredi.service.RepositoryInterface
import com.example.eventssicredi.ui.eventfragment.EventViewModel
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

class EventViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Mock
    private lateinit var postMessageObserver: Observer<Int>

    private lateinit var viewModel: EventViewModel

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `when view model sendCheckin get success then sets success message`(){
        // Arrange
        val checkin = Checkin(1 ,"name" ,"email@email.com")

        val repository = MockRepository(Response.success(checkin))
        viewModel = EventViewModel(repository)
        viewModel.postMessage.observeForever(postMessageObserver)

        // Act
        runBlocking {
            viewModel.handlingResponse(checkin)
        }

        // Assert
        verify(postMessageObserver).onChanged(R.string.connection_success)
    }

    @Test
    fun `when view model sendCheckin get error 400 then sets error 400 from liveData`(){
        // Arrange
        val checkin = Checkin(1 ,"name" ,"email@email.com")
        val responseBody: ResponseBody = Mockito.mock(ResponseBody::class.java)
        Mockito.`when`(responseBody.source()).thenThrow(RuntimeException())
        val resultServerError = MockRepository(Response.error(400, responseBody))
        viewModel = EventViewModel(resultServerError)
        viewModel.postMessage.observeForever(postMessageObserver)

        // Act
        runBlocking {
            viewModel.handlingResponse(checkin)
        }

        // Assert
        verify(postMessageObserver).onChanged(R.string.connection_error_400)
    }

}

class MockRepository(private val response: Response<Checkin>) : RepositoryInterface {

    override suspend fun sendCheckin(userSend: Checkin): Response<Checkin> {
        return response
    }

}