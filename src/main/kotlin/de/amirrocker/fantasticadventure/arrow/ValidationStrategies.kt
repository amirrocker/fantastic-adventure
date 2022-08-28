package de.amirrocker.fantasticadventure.arrow

import arrow.core.Nel
import arrow.core.Valid

sealed class ValidationError(val msg: String) {
    data class DoesNotContain(val value:String) : ValidationError("Does not contain $value")
    data class MaxLength(val value: Int) : ValidationError("Max length exceeded")
    data class NotAnEmail(val reasons : Nel<ValidationError>) : ValidationError("Not a valid email")
}

