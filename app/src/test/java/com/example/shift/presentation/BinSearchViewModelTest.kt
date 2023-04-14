package com.example.shift.presentation

import androidx.lifecycle.Observer
import com.example.shift.data.BinInfoModel
import com.example.shift.data.BinRepository
import com.example.shift.utils.InstantTaskExecutorExtension
import com.example.shift.utils.TestCoroutineExtension
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.io.IOException
import java.io.UncheckedIOException
import java.util.stream.Stream


@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class BinSearchViewModelTest {


    private val repository: BinRepository = mock()
    private val viewModel = BinSearchViewModel(repository)

    private val stateObserver: Observer<SearchState> = mock()

    private companion object {


        @JvmStatic
        fun data(): Stream<Arguments> = Stream.of(
            Arguments.arguments(555555L),
            Arguments.arguments(44444444L),
            Arguments.arguments(55555555L),
        )
    }


    //обычный тест
    @Test
    fun `view model created EXPECT initial state`() {
        Assertions.assertEquals(SearchState.Initial, viewModel.state.value)
    }


    //mock
    @Test
    fun `view model load data EXPECT loading state`() {
        viewModel.state.observeForever(stateObserver)
        val binNum = 0L
        viewModel.loadData(binNum)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(SearchState.Initial)
            verify(stateObserver).onChanged(SearchState.Loading)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `bin loaded EXPECT content state`() = runTest {
        val binNum = 555555L
        val binInfo = BinInfoModel()
        whenever(repository.getByNum(binNum)) doReturn binInfo

        viewModel.loadData(binNum)
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(SearchState.Content(binInfo))

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("data")
    fun `WHEN loaded data EXPECT content state`(binNum: Long) = runTest {
        //чекнул в приложении, на бины с длиной 4- 16 ответ с частичными данными есть
        if (binNum.toString().length in (4..16)) {
            val binInfo = BinInfoModel()
            whenever(repository.getByNum(binNum)) doReturn binInfo
        } else {
            whenever(repository.getByNum(binNum)).thenThrow(Exception())
        }
        viewModel.loadData(binNum)
        Assertions.assertTrue(viewModel.state.value is SearchState.Content)
    }


}