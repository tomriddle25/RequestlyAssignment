package com.giraffehub.requestlyassignment.presentation.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.giraffehub.requestlyassignment.presentation.navigation.CharacterNavigation
import com.giraffehub.requestlyassignment.presentation.theme.Purple500

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharactersScreen(viewModel: CharactersListViewModel, navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Rick and Morty - Characters"
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        if (viewModel.loading.value && viewModel.characters.value.isEmpty()) {
            //TODO show loading
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize(0.2f),
                    color = Purple500
                )
            }
        } else if (viewModel.characters.value.isEmpty()) {
            //TODO show empty state
        } else {
            val lazyColumnState = rememberLazyListState()
            LazyColumn(state = lazyColumnState) {
                val charactersList = viewModel.characters.value
                itemsIndexed(
                    items = charactersList
                ) { index, character ->

                    Card(
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .padding(
                                bottom = 6.dp,
                                top = 6.dp,
                            )
                            .fillMaxWidth()
                            .clickable(onClick = {
                                val route = CharacterNavigation.Character.route + "/${character.id}"
                                navController.navigate(route)
                            })
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape),
                                    model = character.image,
                                    contentDescription = "Thumbnail"
                                )
                                Column(Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Name: " + character.name,
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f)
                                            .wrapContentWidth(Alignment.Start),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Status: " + character.status,
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f)
                                            .wrapContentWidth(Alignment.Start),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Species: " + character.species,
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f)
                                            .wrapContentWidth(Alignment.Start),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Gender: " + character.gender,
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f)
                                            .wrapContentWidth(Alignment.Start),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}