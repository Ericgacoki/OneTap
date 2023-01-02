package com.ericdev.goshopping.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = colorDarkOrange200,
    primaryVariant = colorDarkOrange,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = colorDarkOrange500,
    primaryVariant = colorDarkOrange,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun GoShoppingTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
   /* val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }*/

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}