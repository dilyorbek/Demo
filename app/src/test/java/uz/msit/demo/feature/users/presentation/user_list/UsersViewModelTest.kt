package uz.msit.demo.feature.users.presentation.user_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import uz.msit.demo.feature.users.MainCoroutineRule
import uz.msit.demo.feature.users.domain.failure.GetUsersFailure
import uz.msit.demo.feature.users.domain.repository.UserRepository
import uz.msit.demo.utils.TestData

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class UsersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    @Mock
    private lateinit var mockRepository: UserRepository
    private lateinit var fakeUsersUseCase: FakeGetUsersUseCase
    private lateinit var sut: UsersViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fakeUsersUseCase = FakeGetUsersUseCase(mockRepository)
        sut= UsersViewModel(fakeUsersUseCase)
    }

    @Test
    fun `did shows initial state`() = runTest {
        val expectedState = UsersState(isLoading = true)

        assertEquals(expectedState, sut.state.value)
    }

    @Test
    fun `did shows users when result success`() = runTest {

        fakeUsersUseCase.emit(Result.success(TestData.testUsers))

        launch {
            sut.onEvent(UsersEvent.GetAll)
        }.join()

        assertEquals(UsersState(TestData.testUsers), sut.state.value)
    }

    @Test
    fun `did shows error state when result failure`() = runTest {
        val expectedState = UsersState(isLoading = false, message = "No network connection!")

        fakeUsersUseCase.emit(Result.failure(GetUsersFailure.UnknownError))

        launch {
            sut.onEvent(UsersEvent.GetAll)
        }.join()

        assertEquals(expectedState, sut.state.value)
    }
}