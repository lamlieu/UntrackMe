package io.github.lamlieu.untrackme

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
  // `replay = 1` resolves race condition for if processIntent finishes first before collecting.
  private val _cleanedLink = MutableSharedFlow<String>(replay = 1)
  val cleanedLink = _cleanedLink.asSharedFlow()

  fun processIntent(intent: Intent?) {
    if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
      val link = intent.getStringExtra(Intent.EXTRA_TEXT)
      if (link != null) {
        viewModelScope.launch {
          _cleanedLink.emit(removeTrackingParams(link))
        }
      }
    }
  }

  private fun removeTrackingParams(link: String): String {
    val uri = link.toUri()
    val newUri = uri.buildUpon().clearQuery()
    for (param in uri.queryParameterNames) {
      if (!trackingParams.contains(param)) {
        newUri.appendQueryParameter(param, uri.getQueryParameter(param))
      }
    }
    return newUri.build().toString()
  }

  companion object {
    private val trackingParams: Set<String> = setOf(
      // Instagram
      "igsi",
      "sktn",
      // YouTube
      "si",
    )
  }
}
