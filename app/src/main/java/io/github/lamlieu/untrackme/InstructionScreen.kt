package io.github.lamlieu.untrackme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.lamlieu.untrackme.ui.theme.UntrackMeTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun InstructionScreen(modifier: Modifier = Modifier) {
  val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
  val isVertical = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
  val cardConfigs = InstructionCardConfig.entries

  LazyVerticalGrid(
    columns =
      if (isVertical) {
        GridCells.Fixed(1)
      } else {
        GridCells.Fixed(cardConfigs.size)
      },
    contentPadding = PaddingValues(all = 16.dp),
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(cardConfigs) { config ->
      InstructionCard(config = config)
    }
  }
}

@Composable
private fun InstructionCard(config: InstructionCardConfig, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .background(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(15.dp)
      )
      .padding(all = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(config.instructionRes),
      color = MaterialTheme.colorScheme.onSecondaryContainer
    )
    Spacer(modifier = Modifier.height(8.dp))
    Image(
      modifier = Modifier
        .widthIn(max = 300.dp)
        .clip(RoundedCornerShape(15.dp)),
      painter = painterResource(config.imageRes),
      contentDescription = stringResource(config.imageContentDescriptionRes),
    )
  }
}

private enum class InstructionCardConfig(
  @param:StringRes val instructionRes: Int,
  @param:DrawableRes val imageRes: Int,
  @param:StringRes val imageContentDescriptionRes: Int
) {
  STEP_ONE(
    R.string.instruction_step_1,
    R.drawable.step_one_share_video,
    R.string.instruction_step_1_description
  ),
  STEP_TWO(
    R.string.instruction_step_2,
    R.drawable.step_two_share_link_with_app,
    R.string.instruction_step_2_description
  ),
  STEP_THREE(
    R.string.instruction_step_3,
    R.drawable.step_three_new_link_copied,
    R.string.instruction_step_3_description
  )
}

@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
fun InstructionScreenPreview() {
  UntrackMeTheme { InstructionScreen() }
}
