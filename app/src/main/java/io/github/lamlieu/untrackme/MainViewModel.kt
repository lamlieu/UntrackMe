package io.github.lamlieu.untrackme

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lamlieu.untrackme.MainViewModel.Companion.domainTrackingParamsMap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for processing incoming shared text links, cleaning tracking parameters,
 * and exposing the cleaned URLs via [cleanedLink].
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
  // `replay = 1` resolves race condition for if processIntent finishes first before collecting.
  private val _cleanedLink = MutableSharedFlow<String>(replay = 1)
  val cleanedLink = _cleanedLink.asSharedFlow()

  /**
   * Processes an incoming [Intent], extracting shared text links and emitting cleaned URLs.
   *
   * @param intent The incoming intent, expected to be [Intent.ACTION_SEND] with "text/plain" type.
   */
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

  /**
   * Cleans the given link by removing tracking parameters for configured domains (like YouTube)
   * or removing all query parameters by default for all other websites.
   *
   * @param link The raw URL string to clean.
   * @return The cleaned URL string.
   */
  private fun removeTrackingParams(link: String): String {
    val uri = link.toUri()
    val host = uri.host
    val newUri = uri.buildUpon().clearQuery()

    val trackingParamsToRemove = findTrackingParams(host)
    if (trackingParamsToRemove != null) {
      for (param in uri.queryParameterNames) {
        if (!trackingParamsToRemove.contains(param)) {
          newUri.appendQueryParameter(param, uri.getQueryParameter(param))
        }
      }
    }

    return newUri.build().toString()
  }

  /**
   * Looks up specific tracking parameters to remove for the given host in
   * [domainTrackingParamsMap].
   *
   * @param host The host of the URL (e.g., "youtube.com" or "www.youtube.com").1
   * @return A set of specific tracking parameters to remove, or `null` if all query parameters
   * should be removed by default.
   */
  private fun findTrackingParams(host: String?): Set<String>? {
    val normalizedHost = host?.lowercase()?.removePrefix("www.") ?: return null
    return domainTrackingParamsMap[normalizedHost]
  }

  companion object {
    private val youtubeTrackingParams = setOf("si")

    private val domainTrackingParamsMap: Map<String, Set<String>> = mapOf(
      // YouTube
      "youtube.com" to youtubeTrackingParams,
      "youtu.be" to youtubeTrackingParams
    )
  }
}
