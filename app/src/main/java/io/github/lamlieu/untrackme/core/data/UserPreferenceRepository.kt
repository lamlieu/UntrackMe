package io.github.lamlieu.untrackme.core.data

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
  val useOgInstagram: Flow<Boolean>
  suspend fun setUseOgInstagram(enabled: Boolean)
}
