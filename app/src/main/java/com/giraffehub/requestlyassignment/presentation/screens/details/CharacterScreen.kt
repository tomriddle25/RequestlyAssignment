package com.giraffehub.requestlyassignment.presentation.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterScreen(viewModel: CharacterDetailsViewModel, characterId: Int) {
    Scaffold {
        viewModel.fetchCharacter(characterId)
        if (viewModel.loading.value && viewModel.character.value == null) {
            //TODO show loading
        } else if (!viewModel.loading.value && viewModel.character.value == null) {
            //TODO show error state
        } else {
            viewModel.character.value?.let { character ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(248.dp)
                            .clip(CircleShape),
                        model = character.image,
                        contentDescription = "Thumbnail"
                    )
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
