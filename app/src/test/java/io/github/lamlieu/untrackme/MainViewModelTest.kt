package io.github.lamlieu.untrackme

import android.content.Intent
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
class MainViewModelTest {

  @get:Rule val hiltRule = HiltAndroidRule(this)

  @Inject lateinit var userPreferenceRepository: UserPreferenceRepository

  private lateinit var viewModel: MainViewModel

  @Before
  fun setUp() {
    hiltRule.inject()
    viewModel = MainViewModel(userPreferenceRepository)
  }

  @Test
  fun processIntent_youtube_onlyRemovesSiQueryParameter() = runBlocking {
    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, "https://youtu.be/abc?v=123&si=xyz")
    }

    viewModel.processIntent(intent)
    val cleaned = viewModel.cleanedLink.first()

    assertEquals("https://youtu.be/abc?v=123", cleaned)
  }

  @Test
  fun processIntent_defaultFallback_removesAllQueryParams() = runBlocking {
    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, "https://example.com/page?foo=bar&utm_medium=social")
    }

    viewModel.processIntent(intent)
    val cleaned = viewModel.cleanedLink.first()

    assertEquals("https://example.com/page", cleaned)
  }

  @Test
  fun processIntent_instagram_useOgInstagramTrue_replacesLinkWithOgInstagram() = runBlocking {
    userPreferenceRepository.setUseOgInstagram(true)

    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, "https://www.instagram.com/p/XYZ/?igshid=abc")
    }

    viewModel.processIntent(intent)
    val cleaned = viewModel.cleanedLink.first()

    assertEquals("https://www.d.oginstagram.com/p/XYZ/", cleaned)
  }

  @Test
  fun processIntent_instagram_useOgInstagramFalse_linkRemainsAsInstagram() = runBlocking {
    userPreferenceRepository.setUseOgInstagram(false)

    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, "https://www.instagram.com/p/XYZ/?igshid=abc")
    }

    viewModel.processIntent(intent)
    val cleaned = viewModel.cleanedLink.first()

    assertEquals("https://www.instagram.com/p/XYZ/", cleaned)
  }
}
