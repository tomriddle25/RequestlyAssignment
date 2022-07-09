package com.giraffehub.requestlyassignment.presentation.navigation

sealed class CharacterNavigation(val route: String) {
    object Characters : CharacterNavigation("characters")

    object Character : CharacterNavigation("character")
}