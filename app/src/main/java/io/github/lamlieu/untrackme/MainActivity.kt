@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.lamlieu.untrackme

import android.content.Intent.ACTION_SEND
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import io.github.lamlieu.untrackme.ui.theme.UntrackMeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val isTextShareIntent: Boolean
    get() = intent?.action == ACTION_SEND && intent?.type == "text/plain"

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    if (isTextShareIntent) {
      viewModel.processIntent(intent)
    }

    setContent {
      if (isTextShareIntent) {
        SharesheetHandler(viewModel = viewModel, onComplete = { finish() })
      } else {
        Instructions()
      }
    }
  }

  @Composable
  fun Instructions() {
    UntrackMeTheme {
      Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(title = {
          Text(text = stringResource(R.string.app_name))
        })
      }) { innerPadding ->
        InstructionScreen(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        )
      }
    }
  }
}