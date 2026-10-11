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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import io.github.lamlieu.untrackme.settings.SettingsScreen
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
      UntrackMeTheme {
        Surface(tonalElevation = 5.dp) {
          if (isTextShareIntent) {
            SharesheetHandler(viewModel = viewModel, onComplete = { finish() })
          } else {
            Instructions()
          }
        }
      }
    }
  }

  @Composable
  fun Instructions() {
    var showSettings by remember { mutableStateOf(false) }

    Scaffold(
      modifier = Modifier.fillMaxSize(),
      topBar = {
        CenterAlignedTopAppBar(
          title = {
            Text(
              text = if (showSettings) {
                stringResource(R.string.settings_title)
              } else {
                stringResource(R.string.main_activity_top_app_bar_title)
              }
            )
          },
          navigationIcon = {
            if (showSettings) {
              IconButton(onClick = { showSettings = false }) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = stringResource(R.string.back_content_description)
                )
              }
            }
          },
          actions = {
            if (!showSettings) {
              IconButton(onClick = { showSettings = true }) {
                Icon(
                  imageVector = Icons.Default.Settings,
                  contentDescription = stringResource(R.string.settings_content_description)
                )
              }
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
          )
        )
      }
    ) { innerPadding ->
      if (showSettings) {
        SettingsScreen(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        )
      } else {
        InstructionScreen(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        )
      }
    }
  }
}
