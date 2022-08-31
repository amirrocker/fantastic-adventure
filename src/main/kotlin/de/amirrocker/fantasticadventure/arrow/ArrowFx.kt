package de.amirrocker.fantasticadventure.arrow

import arrow.fx.coroutines.Atomic

// looking at Atomic:

// using lens to create an atomic reference for B based on provided A get set operations.

data class Preference(val isEnabled: Boolean)
data class User(val name: Name, val age: Age, val preference: Preference)
data class ViewState(val user: User)

suspend fun main(): Unit {

    val state: Atomic<ViewState> =
        Atomic(ViewState(User(Name("Simon"), Age(25), Preference(false))))
    val isEnabled: Atomic<Boolean> = state.lens(
        get = { it.user.preference.isEnabled },
        set = { stateValue, isEnabled ->
            stateValue.copy(
                user = stateValue.user.copy(
                    preference = stateValue.user.preference.copy(isEnabled = isEnabled)
                )
            )
        }
    )
    isEnabled.set(true)
    println("state: ${state.get()}")

    isEnabled.set(false)
    println("state: ${state.get()}")




}
