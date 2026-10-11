package io.github.lamlieu.untrackme.settings

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class SettingsViewModelTest {

  @get:Rule val hiltRule = HiltAndroidRule(this)

  @Inject lateinit var userPreferenceRepository: UserPreferenceRepository

  private lateinit var viewModel: SettingsViewModel

  @Before
  fun setUp() {
    hiltRule.inject()
    viewModel = SettingsViewModel(userPreferenceRepository)
  }

  @Test
  fun useOgInstagram_default_isFalse() = runBlocking {
    userPreferenceRepository.setUseOgInstagram(false)

    val value = viewModel.useOgInstagram.first()

    assertEquals(false, value)
  }

  @Test
  fun setUseOgInstagram_true_updatesStateToTrue() = runBlocking {
    viewModel.setUseOgInstagram(true)

    val value = viewModel.useOgInstagram.first()

    assertEquals(true, value)
  }

  @Test
  fun setUseOgInstagram_false_updatesStateToFalse() = runBlocking {
    viewModel.setUseOgInstagram(false)

    val value = viewModel.useOgInstagram.first()

    assertEquals(false, value)
  }
}
