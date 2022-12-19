package com.ericdev.goshopping.feature_sign_up.util

sealed class Validity(val message: String = ""){
    object Valid: Validity()
    data class InValid(val reason: String): Validity(reason)
}
