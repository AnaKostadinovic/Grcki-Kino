package com.anak.grckikino.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anak.grckikino.R
import com.anak.grckikino.models.GameData


@Composable
fun GameListScreen(
    gamesList: List<GameData>?,
    onGameClickCallback: (Int) -> Unit,
    onGameExpired: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        GameListHeader()

        GameList(
            gamesList = gamesList,
            onGameClickCallback = onGameClickCallback,
            onGameExpired
        )
    }
}

@Composable
fun GameList(
    gamesList: List<GameData>?,
    onGameClickCallback: (Int) -> Unit,
    onGameExpired: () -> Unit,
) {
    if (gamesList != null) {
        LazyColumn {
            items(gamesList) { listItem ->
                GameListItem(
                    drawId = listItem.drawId,
                    startTime = listItem.drawTime,
                    clickable = true,
                    onGameClickCallback = onGameClickCallback,
                    onGameExpired = onGameExpired
                )
            }
        }
    }
}

@Composable
fun GameListHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .width(dimensionResource(id = R.dimen.flag_width))
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.padding_medium)),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 16.sp
                    ),
                    text = stringResource(id = R.string.game_name),
                    textAlign = TextAlign.Center,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    text = stringResource(id = R.string.game_time),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.weight(6f))
                Text(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    text = stringResource(id = R.string.remaining_time),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp
                    )
                )
            }

            Divider(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.inversePrimary,
                thickness = dimensionResource(id = R.dimen.divider_line_thickness)
            )
        }
    }
}
