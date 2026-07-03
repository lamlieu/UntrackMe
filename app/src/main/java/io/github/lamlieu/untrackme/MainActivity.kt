@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.lamlieu.untrackme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.lamlieu.untrackme.ui.theme.UntrackMeTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      UntrackMeTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
          CenterAlignedTopAppBar(title = {
            Text(text = stringResource(R.string.app_name))
          })
        }) { innerPadding ->
          OverviewScreen(
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding)
          )
        }
      }
    }
  }
}