package io.github.lamlieu.untrackme.core.data.testing

import io.github.lamlieu.untrackme.core.data.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FakeUserPreferenceRepository @Inject constructor() : UserPreferenceRepository {
  private val _useOgInstagram = MutableStateFlow(false)
  override val useOgInstagram: Flow<Boolean> = _useOgInstagram

  override suspend fun setUseOgInstagram(enabled: Boolean) {
    _useOgInstagram.value = enabled
  }
}
