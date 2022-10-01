package uz.msit.demo.users.presentation.user_list.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uz.msit.demo.users.domain.model.User
import uz.msit.demo.users.presentation.user_list.UserItem

class UserItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val user = User(1, "sasa", "vava")

    @Before
    fun setUp() {
        composeTestRule.setContent { UserItem(user = user) }
    }

    @Test
    fun did_display_username_and_photo() {
        composeTestRule.onNodeWithText(user.login).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("user_photo").assertIsDisplayed()
    }
}