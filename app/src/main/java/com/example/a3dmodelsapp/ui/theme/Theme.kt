package com.example.a3dmodelsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CustomTextStyles {


    // Заголовки
    val heading_large = TextStyle(fontSize = 24.sp, lineHeight = 32.sp, fontWeight = FontWeight.Bold)
    val heading_medium = TextStyle(fontSize = 20.sp, lineHeight = 28.sp)
    val heading_small = TextStyle(fontSize = 18.sp, lineHeight = 24.sp)

    // Подзаголовки
    val sub_heading_regular = TextStyle(fontSize = 16.sp, lineHeight = 24.sp)
    val sub_heading_medium = TextStyle(fontSize = 16.sp, lineHeight = 24.sp)

    // Основной текст
    val body1_regular = TextStyle( fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal)
    val body1_medium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium)
    val body1_semi_bold = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold)
    val body1_bold = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Bold)

    // Вспомогательный текст
    val body2_regular = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal)
    val body2_medium = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium)
    val body2_semi_bold = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.SemiBold)
    val body2_bold = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Bold)


    val caption_regular = TextStyle(fontSize = 11.sp, lineHeight = 16.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
}

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = backgroundColor,
    surface = backgroundColor
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = backgroundColor,
    surface = backgroundColor,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = textColor,
    onSurface = textColor,
)

@Composable
fun _3DModelsAppTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}