package com.test.i_comment.validator

object FieldValidator {

    private const val MIN_FIELD_LENGTH = 1

    fun validate(field: String): Boolean {
        return field.trim().length >= MIN_FIELD_LENGTH
    }

    fun validateLower(lowerBound: String, upperBound: String): Boolean {
        return try {
            val lower = lowerBound.toInt()
            val upper = upperBound.toInt()
            lower < upper
        } catch (e: Exception) {
            false
        }
    }

    fun validateUpper(upperBound: String, lowerBound: String): Boolean {
        return try {
            val lower = lowerBound.toInt()
            val upper = upperBound.toInt()
            upper > lower
        } catch (e: Exception) {
            false
        }
    }

}