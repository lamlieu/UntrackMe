package io.github.lamlieu.untrackme.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepository
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserPreferenceRepositoryTest {

  private lateinit var repository: UserPreferenceRepository

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    repository = UserPreferenceRepositoryImpl(context)
  }

  @Test
  fun defaultValue_isFalse() = runBlocking {
    repository.setUseOgInstagram(false)

    val value = repository.useOgInstagram.first()
    assertFalse(value)
  }

  @Test
  fun setUseOgInstagram_true_updatesValueToTrue() = runBlocking {
    repository.setUseOgInstagram(true)

    val value = repository.useOgInstagram.first()
    assertTrue(value)
  }

  @Test
  fun setUseOgInstagram_false_updatesValueToFalse() = runBlocking {
    repository.setUseOgInstagram(false)

    val value = repository.useOgInstagram.first()
    assertFalse(value)
  }
}
