package io.github.lamlieu.untrackme.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val userPreferenceRepository: UserPreferenceRepository
) : ViewModel() {

  val useOgInstagram: StateFlow<Boolean> = userPreferenceRepository.useOgInstagram
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = false
    )

  fun setUseOgInstagram(enabled: Boolean) {
    viewModelScope.launch {
      userPreferenceRepository.setUseOgInstagram(enabled)
    }
  }
}