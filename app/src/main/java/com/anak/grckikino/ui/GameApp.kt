package com.anak.grckikino.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.anak.grckikino.R
import com.anak.grckikino.viewmodels.GameViewModel

@Composable
fun GameApp(gameViewModel: GameViewModel = viewModel()) {
    val navController = rememberNavController()
    val gamesList = gameViewModel.upcomingGames.collectAsState()
    val gameData = gameViewModel.gameData.collectAsState()
    val chooseNumbersSize = gameViewModel.canChooseNumbers.collectAsState()
    val resultsData = gameViewModel.results.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colorScheme.primary,
                elevation = 4.dp
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.GameList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.GameList) {
                GameListScreen(
                    gamesList = gamesList.value,
                    onGameClickCallback = { drawId ->
                        gameViewModel.getGameByDrawId(drawId)
                        navController.navigate(Screen.Game)
                    },
                    onGameExpired = { gameViewModel.refreshUpcomingEvent() },
                )
            }
            composable(Screen.Game) {
                GameScreen(
                    gameData = gameData.value,
                    canChooseNumbers = chooseNumbersSize.value < 15,
                    chooseNumbersSize = chooseNumbersSize.value,
                    onSelectNumber = gameViewModel::selectNumber,
                    onUnselectNumber = gameViewModel::unselectNumber
                ) {
                    navController.navigate(Screen.GameList)
                }
            }
            composable(Screen.Results) {
                ResultsScreen(resultsData = resultsData)
            }
            composable(Screen.LiveGame) {
                LiveGameScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary, elevation = 8.dp) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Live Game") },
            label = { Text("Live") },
            selected = currentRoute == Screen.LiveGame,
            onClick = { navController.navigate(Screen.LiveGame) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Game List") },
            label = { Text("Game List") },
            selected = currentRoute == Screen.GameList,
            onClick = { navController.navigate(Screen.GameList) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Results") },
            label = { Text("Results") },
            selected = currentRoute == Screen.Results,
            onClick = { navController.navigate(Screen.Results) }
        )
    }
}

object Screen {
    const val GameList = "game_list"
    const val Game = "game"
    const val Results = "results"
    const val LiveGame = "live_game"
}
