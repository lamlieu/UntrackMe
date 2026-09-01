package io.github.lamlieu.untrackme

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard

@Composable
fun SharesheetHandler(
  viewModel: MainViewModel,
  onComplete: () -> Unit = {}
) {
  val clipboard = LocalClipboard.current
  val currentOnComplete by rememberUpdatedState(onComplete)

  LaunchedEffect(viewModel) {
    viewModel.cleanedLink.collect { cleanLink ->
      val clipData = ClipData.newPlainText("plain text", cleanLink)
      clipboard.setClipEntry(ClipEntry(clipData))
      currentOnComplete()
    }
  }
}
