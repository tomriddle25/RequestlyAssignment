package com.giraffehub.requestlyassignment.domain.repository

import com.giraffehub.requestlyassignment.data.network.model.Character
import com.giraffehub.requestlyassignment.data.network.service.CharactersService
import com.giraffehub.requestlyassignment.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRepository(
    private val characterService: CharactersService
) {
    var lastPage = -1

    fun getAll(page: Int): Flow<DataState<List<Character>>> = flow {
        try {
            emit(DataState.loading())
            val characters = characterService.getCharacters(page)
            lastPage = characters.info.pages
            emit(DataState.success(characters.results))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }


    fun getById(id: Int): Flow<DataState<Character>> = flow {
        try {
            emit(DataState.loading())
            val character = characterService.getCharacter(id)
            emit(DataState.success(character))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }

}