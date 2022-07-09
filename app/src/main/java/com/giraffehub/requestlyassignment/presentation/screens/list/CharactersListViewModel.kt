package com.giraffehub.requestlyassignment.presentation.screens.list

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
class CharactersListViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
) : ViewModel() {
    val characters: MutableState<List<Character>> = mutableStateOf(emptyList())
    val loading = mutableStateOf(false)
    val currentPage = mutableStateOf(0)
    val lastPage = mutableStateOf(-1)
    var nextFetchTriggered = false

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        if (currentPage.value + 1 != charactersRepository.lastPage) {
            currentPage.value += 1
            nextFetchTriggered = true
            charactersRepository.getAll(currentPage.value).onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let {
                    characters.value = it
                    currentPage.value = it.size / 20
                    lastPage.value = charactersRepository.lastPage
                    nextFetchTriggered = false
                }
                dataState.error?.let {
                }
            }.launchIn(viewModelScope)
        }
    }
}