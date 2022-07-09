package com.giraffehub.requestlyassignment.presentation.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffehub.requestlyassignment.data.network.model.Character
import com.giraffehub.requestlyassignment.domain.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
) : ViewModel() {
    val character: MutableState<Character?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    fun fetchCharacter(characterId: Int) {
        if (character.value == null) {
            charactersRepository.getById(characterId).onEach { dataState ->
                loading.value = dataState.loading
                dataState.data?.let { data ->
                    character.value = data
                }
                dataState.error?.let { error ->
                    //TODO Show some error
                }
            }.launchIn(viewModelScope)
        }
    }
}