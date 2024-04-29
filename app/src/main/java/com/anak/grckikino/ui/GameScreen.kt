package com.anak.grckikino.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.anak.grckikino.R
import com.anak.grckikino.models.GameData
import com.anak.grckikino.util.toTimeFormat
import kotlin.math.pow

@Composable
fun GameScreen(
    gameData: GameData?,
    canChooseNumbers: Boolean,
    chooseNumbersSize: Int,
    onSelectNumber: (Int) -> Unit,
    onUnselectNumber: (Int) -> Unit,
    onExit: () -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }

    if (gameData != null) {

        LaunchedEffect(key1 = gameData) {
            showDialog.value = false
        }

        if (showDialog.value) {
            CustomDialog {
                onExit()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.secondary)
        ) {

            TimeAndGame(time = gameData.drawTime.toTimeFormat(), game = gameData.drawId.toString())

            QuotesRow(
                quotesList = listOf(
                    stringResource(id = R.string.number_of_balls),
                    stringResource(id = R.string.one),
                    stringResource(id = R.string.two),
                    stringResource(id = R.string.three),
                    stringResource(id = R.string.four),
                    stringResource(id = R.string.five),
                    stringResource(id = R.string.six),
                    stringResource(id = R.string.seven)
                )
            )
            Divider(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.divider_line_padding_start)),
                color = colorScheme.onPrimary,
                thickness = dimensionResource(id = R.dimen.divider_line_thickness)
            )
            QuotesRow(
                quotesList = listOf(
                    stringResource(id = R.string.quote),
                    stringResource(id = R.string.quote_1),
                    stringResource(id = R.string.quote_2),
                    stringResource(id = R.string.quote_3),
                    stringResource(id = R.string.quote_4),
                    stringResource(id = R.string.quote_5),
                    stringResource(id = R.string.quote_6),
                    stringResource(id = R.string.quote_7)
                )
            )

            for (i in 0..7) {
                RowOfBalls(
                    startNumber = i * 10,
                    canBeClicked = canChooseNumbers,
                    numberOfElements = 10
                ) { number, isSelected ->
                    if (isSelected) {
                        onSelectNumber(number)
                    } else {
                        onUnselectNumber(number)
                    }
                }
            }

            GameListItem(
                drawId = gameData.drawId,
                startTime = gameData.drawTime,
                clickable = false,
                onGameClickCallback = {}
            ) {
                showDialog.value = true
            }

            Row(
                modifier = Modifier
                    .background(colorScheme.secondary)
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.padding_small))
                        .weight(2f),
                    text = stringResource(id = R.string.quote),
                    style = TextStyle(color = colorScheme.onPrimary, fontSize = 20.sp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier
                        .weight(7f),
                    text = stringResource(id = R.string.total_number),
                    style = TextStyle(color = colorScheme.onPrimary, fontSize = 20.sp)
                )
            }

            Row(
                modifier = Modifier
                    .background(colorScheme.secondary)
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth()
            ) {
                val result = 3.75.pow(chooseNumbersSize)

                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.padding_small))
                        .weight(7f),
                    text = String.format("%.2f", result),
                    style = TextStyle(color = colorScheme.onPrimary, fontSize = 20.sp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = chooseNumbersSize.toString(),
                    style = TextStyle(color = colorScheme.onPrimary, fontSize = 20.sp)
                )
            }
        }
    }
}