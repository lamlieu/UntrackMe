package io.github.lamlieu.untrackme

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

  private lateinit var viewModel: MainViewModel

  @Before
  fun setUp() {
    viewModel = MainViewModel()
  }

  @Test
  fun processIntent_youtube_onlyRemovesSiQueryParameter() = runBlocking {
    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, "https://youtu.be/abc?v=123&si=xyz")
    }

    viewModel.processIntent(intent)
    val cleaned = viewModel.cleanedLink.first()

    Assert.assertEquals("https://youtu.be/abc?v=123", cleaned)
  }

  @Test
  fun processIntent_defaultFallback_removesAllQueryParams() = runBlocking {
    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, "https://example.com/page?foo=bar&utm_medium=social")
    }

    viewModel.processIntent(intent)
    val cleaned = viewModel.cleanedLink.first()

    Assert.assertEquals("https://example.com/page", cleaned)
  }
}