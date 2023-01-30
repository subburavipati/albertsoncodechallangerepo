package com.subbu.albertsonstask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.subbu.albertsonstask.FakeAcronymRepository
import com.subbu.albertsonstask.FakeAcronymRepository.Companion.listOfTestWords
import com.subbu.albertsonstask.model.data.WordData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class AcronymViewModelTest {

    private lateinit var viewModel: AcronymViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val repository = FakeAcronymRepository()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AcronymViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given getLfs action, when response is successful, then post Success event`() {
        val shortForm = "NASA"

        runTest {
            viewModel.getLfs(shortForm)
            advanceUntilIdle()
            val actualResult = viewModel.acronymEvent.value as? AcronymViewModel.AcronymEvent.Success
            assertEquals(listOfTestWords.first().lfs.map {
                WordData(it.lf, it.freq, it.since)
            }, actualResult?.response)
        }
    }

    @Test
    fun `Given getLfs action, when input is empty then response is empty`() {
        val shortForm = ""

        runTest {
            viewModel.getLfs(shortForm)
            advanceUntilIdle()
            val actualResult = viewModel.acronymEvent.value as AcronymViewModel.AcronymEvent.Failure
            assertEquals(emptyList<WordData>(), actualResult.errorMessage)
        }
    }
}