package com.giraffehub.requestlyassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.giraffehub.requestlyassignment.presentation.navigation.CharacterNavigation
import com.giraffehub.requestlyassignment.presentation.screens.details.CharacterDetailsViewModel
import com.giraffehub.requestlyassignment.presentation.screens.details.CharacterScreen
import com.giraffehub.requestlyassignment.presentation.screens.list.CharactersListViewModel
import com.giraffehub.requestlyassignment.presentation.screens.list.CharactersScreen
import com.giraffehub.requestlyassignment.presentation.theme.RequestlyAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RequestlyAssignmentTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = CharacterNavigation.Characters.route
                ) {
                    composable(CharacterNavigation.Characters.route) {
                        val factory = HiltViewModelFactory(LocalContext.current, it)
                        val viewModel: CharactersListViewModel =
                            viewModel(key = "CharactersViewModel", factory = factory)
                        CharactersScreen(viewModel = viewModel, navController = navController)
                    }
                    composable(
                        route = CharacterNavigation.Character.route + "/{characterId}",
                        arguments = listOf(navArgument("characterId") {
                            type = NavType.IntType
                        })
                    ) { navBackStackEntry ->
                        navBackStackEntry.arguments?.getInt("characterId")?.let { id ->
                            val factory =
                                HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                            val viewModel: CharacterDetailsViewModel =
                                viewModel(key = "CharacterViewModel", factory = factory)
                            CharacterScreen(viewModel = viewModel, characterId = id)
                        }
                    }
                }
            }
        }
    }
}
