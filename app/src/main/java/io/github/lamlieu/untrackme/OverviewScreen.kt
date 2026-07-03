package io.github.lamlieu.untrackme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OverviewScreen(modifier: Modifier = Modifier) {
  var buttonState by rememberSaveable { mutableStateOf(ButtonState.NOT_STARTED) }
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Tap to remove tracker from your share links!",
      modifier = Modifier.padding(8.dp)
    )
    Button(
      onClick = {
        buttonState = when (buttonState) {
          ButtonState.NOT_STARTED -> ButtonState.STARTED
          ButtonState.STARTED -> ButtonState.NOT_STARTED
        }
      },
      modifier = Modifier.padding(8.dp)
    ) { Text(text = buttonState.text) }
    Spacer(modifier = Modifier.fillMaxWidth().padding(80.dp))
  }
}

enum class ButtonState(val text: String) {
  NOT_STARTED("Enable"),
  STARTED("Disable"),
}