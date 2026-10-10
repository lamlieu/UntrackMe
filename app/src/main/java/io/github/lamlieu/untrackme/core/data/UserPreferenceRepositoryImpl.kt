package io.github.lamlieu.untrackme.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferenceRepositoryImpl @Inject constructor(
  @param:ApplicationContext private val context: Context
) : UserPreferenceRepository {
  private object PreferencesKeys {
    val USE_OG_INSTAGRAM = booleanPreferencesKey("use_og_instagram")
  }

  override val useOgInstagram: Flow<Boolean> = context.dataStore.data
    .map { preferences ->
      preferences[PreferencesKeys.USE_OG_INSTAGRAM] ?: false
    }

  override suspend fun setUseOgInstagram(enabled: Boolean) {
    context.dataStore.edit { preferences ->
      preferences[PreferencesKeys.USE_OG_INSTAGRAM] = enabled
    }
  }
}
