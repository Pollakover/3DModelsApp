package com.example.a3dmodelsapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CustomTextStyles {

    //Heading 2

//    val heading2_regular = TextStyle(
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        fontWeight = FontWeight.Normal,
//        fontFamily = fontFamily
//    )
//
//    val heading2_medium = TextStyle(
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        fontWeight = FontWeight.Medium,
//        fontFamily = fontFamily
//    )
//
//    val heading2_semi_bold = TextStyle(
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        fontWeight = FontWeight.SemiBold,
//        fontFamily = fontFamily
//    )
//
//    val heading2_bold = TextStyle(
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = fontFamily
//    )
//
//    //Heading 1
//
//    val heading1_regular = TextStyle(
//        fontSize = 30.sp,
//        lineHeight = 38.sp,
//        fontWeight = FontWeight.Normal,
//        fontFamily = fontFamily
//    )
//
//    val heading1_medium = TextStyle(
//        fontSize = 30.sp,
//        lineHeight = 38.sp,
//        fontWeight = FontWeight.Medium,
//        fontFamily = fontFamily
//    )
//
//    val heading1_semi_bold = TextStyle(
//        fontSize = 30.sp,
//        lineHeight = 38.sp,
//        fontWeight = FontWeight.SemiBold,
//        fontFamily = fontFamily
//    )
//
//    val heading1_bold = TextStyle(
//        fontSize = 30.sp,
//        lineHeight = 38.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = fontFamily
//    )
//
//    //Sub heading
//
////    val sub_heading_regular = TextStyle(
////        fontSize = 20.sp,
////        lineHeight = 30.sp,
////        fontWeight = FontWeight.Normal,
////        fontFamily = fontFamily
////    )
////
////    val sub_heading_medium = TextStyle(
////        fontSize = 20.sp,
////        lineHeight = 30.sp,
////        fontWeight = FontWeight.Medium,
////        fontFamily = fontFamily
////    )
//
//    val sub_heading_semi_bold = TextStyle(
//        fontSize = 20.sp,
//        lineHeight = 30.sp,
//        fontWeight = FontWeight.SemiBold,
//        fontFamily = fontFamily
//    )
//
//    val sub_heading_bold = TextStyle(
//        fontSize = 20.sp,
//        lineHeight = 30.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = fontFamily
//    )
//
//    //Body 1
//
////    val body1_regular = TextStyle(
////        fontSize = 16.sp,
////        lineHeight = 24.sp,
////        fontWeight = FontWeight.Normal,
////        fontFamily = fontFamily
////    )
////
////    val body1_medium = TextStyle(
////        fontSize = 16.sp,
////        lineHeight = 24.sp,
////        fontWeight = FontWeight.Medium,
////        fontFamily = fontFamily
////    )
//
//    val body1_semi_bold = TextStyle(
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        fontWeight = FontWeight.SemiBold,
//        fontFamily = fontFamily
//    )
//
//    val body1_bold = TextStyle(
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = fontFamily
//    )
//
//    //Body 2
////    val body2_regular = TextStyle(
////        fontSize = 14.sp,
////        lineHeight = 20.sp,
////        fontWeight = FontWeight.Normal,
////        fontFamily = fontFamily
////    )
////
////    val body2_medium = TextStyle(
////        fontSize = 14.sp,
////        lineHeight = 20.sp,
////        fontWeight = FontWeight.Medium,
////        fontFamily = fontFamily
////    )
//
//    val body2_semi_bold = TextStyle(
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        fontWeight = FontWeight.SemiBold,
//        fontFamily = fontFamily
//    )
//
//    val body2_bold = TextStyle(
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = fontFamily
//    )

    // Заголовки
    val heading_large = TextStyle(fontSize = 24.sp, lineHeight = 32.sp)
    val heading_medium = TextStyle(fontSize = 20.sp, lineHeight = 28.sp)
    val heading_small = TextStyle(fontSize = 18.sp, lineHeight = 24.sp)

    // Подзаголовки
    val sub_heading_regular = TextStyle(fontSize = 16.sp, lineHeight = 24.sp)  // ← ваш текущий
    val sub_heading_medium = TextStyle(fontSize = 16.sp, lineHeight = 24.sp)

    // Основной текст
    val body1_regular = TextStyle(fontSize = 14.sp, lineHeight = 20.sp)  // ← ваш текущий
    val body1_medium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp)    // ← ваш текущий

    // Вспомогательный текст
    val body2_regular = TextStyle(fontSize = 12.sp, lineHeight = 16.sp)   // ← ваш текущий
    val body2_medium = TextStyle(fontSize = 12.sp, lineHeight = 16.sp)










    val caption_regular = TextStyle(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = backgroundColor,
    surface = Color(0xFFFFFBFE),
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