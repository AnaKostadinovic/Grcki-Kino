package com.anak.grckikino.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.anak.grckikino.R
import com.anak.grckikino.models.GameData
import com.anak.grckikino.util.toCountDownTime
import com.anak.grckikino.util.toTimeFormat
import kotlinx.coroutines.delay


/**
 * TIME
 */
@Composable
fun TimeAndGame(
    time: String,
    game: String
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.time_and_game, time, game),
            style = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
        )
    }
}

/**
 * QUOTES
 */
@Composable
fun QuotesRow(
    quotesList: List<String>
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
    ) {
        quotesList.forEach { quote ->
            QuotesText(text = quote)
        }
    }
}

@Composable
fun RowScope.QuotesText(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .weight(1f),
        text = text,
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp
        )
    )
}

/**
 * BALLS
 */
@Composable
fun RowOfBalls(
    startNumber: Int,
    numberOfElements: Int,
    canBeClicked: Boolean,
    onElementClick: (Int, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 1..numberOfElements) {
            val currentNumber = startNumber + i
            Ball(
                number = currentNumber,
                hasOuterBorder = true,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                canBeClicked = canBeClicked,
                onClick = onElementClick
            )
        }
    }
}

@Composable
fun Ball(
    number: Int,
    hasOuterBorder: Boolean,
    modifier: Modifier = Modifier,
    canBeClicked: Boolean,
    onClick: (Int, Boolean) -> Unit,
    isResultBall: Boolean = false
) {

    var highlighted by remember {
        if (isResultBall) {
            mutableStateOf(true)
        } else {
            mutableStateOf(false)
        }
    }

    val color = if (number < 11) {
        Color.Yellow
    } else if (number < 21) {
        Color(255, 165, 0)
    } else if (number < 31) {
        Color.Red
    } else if (number < 41) {
        Color(255, 192, 203)
    } else if (number < 51) {
        Color(128, 0, 128)
    } else if (number < 61) {
        Color(64, 224, 208)
    } else if (number < 71) {
        Color.Green
    } else {
        Color.Blue
    }

    val borderColor = if (highlighted) color else Color.Transparent

    Box(
        modifier = modifier
            .clickable {
                if (!isResultBall && (canBeClicked || highlighted)) {
                    highlighted = !highlighted
                    onClick(number, highlighted)
                }
            }
            .let {
                if (hasOuterBorder) {
                    it.border(
                        dimensionResource(id = R.dimen.border_default_thickness),
                        MaterialTheme.colorScheme.secondary
                    )
                } else {
                    it
                }
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(dimensionResource(id = R.dimen.padding_small))
            .border(
                dimensionResource(id = R.dimen.border_thick),
                borderColor,
                shape = RoundedCornerShape(30.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun GameListItem(
    drawId: Int,
    startTime: Long,
    clickable: Boolean,
    onGameClickCallback: (Int) -> Unit,
    onGameExpired: () -> Unit,
) {
    val remainingTime = remember { mutableStateOf(startTime - System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (remainingTime.value > 0) {
            delay(1000)
            remainingTime.value -= 1000
        }
        if (remainingTime.value <= 0) {
            onGameExpired()
        }
    }

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .height(50.dp)
            .then(
                if (clickable) Modifier.clickable { onGameClickCallback(drawId) }
                else Modifier
            )
    ) {
        if (clickable) {
            Text(
                text = startTime.toTimeFormat(),
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
                    .weight(5f),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp
                )
            )
        } else {
            Text(
                text = stringResource(id = R.string.remaining_time),
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
                    .weight(5f),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        val timerColor = if (remainingTime.value <= 30000) {
            Color.Red
        } else {
            Color.White
        }
        Text(
            text = remainingTime.value.toCountDownTime(),
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )
                .weight(5f),
            textAlign = TextAlign.End,
            style = TextStyle(
                color = timerColor,
                fontSize = 16.sp
            )
        )
    }
    Divider(
        color = MaterialTheme.colorScheme.secondary,
        thickness = dimensionResource(id = R.dimen.divider_line_thickness)
    )
}

@Composable
fun SingleResult(
    data: GameData
) {
    TimeAndGame(time = data.drawTime.toTimeFormat(), game = data.drawId.toString())
    ResultNumbers(numbersList = data.winningNumbers.list)
}

@Composable
fun ResultNumbers(
    numbersList: List<Int>
) {
    numbersList.chunked(7).forEach { singleRow ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            singleRow.forEach { result ->
                Ball(
                    number = result,
                    hasOuterBorder = false,
                    canBeClicked = false,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    isResultBall = true,
                    onClick = { _, _ -> }
                )
            }

            val additionalSpacerWidth = (7 - singleRow.size).toFloat()
            if (additionalSpacerWidth > 0) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun CustomDialog(onExitClick: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Sorry, the game has already started.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onExitClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Exit")
                }
            }
        }
    }
}