package com.example.a3dmodelsapp.screens.modelInteractions.viewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.gradient1
import com.example.a3dmodelsapp.ui.theme.gradient2
import com.example.a3dmodelsapp.ui.theme.textColor

@Composable
fun ViewerScreen(MINavController: NavController) {
    Scaffold() {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.linearGradient(
                        colors = listOf(gradient1, gradient2),
                        start = Offset(0f, 0f)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        colors = IconButtonColors(
                            containerColor = Color(0xFF171A1F).copy(alpha = 0.3f),
                            contentColor = textColor,
                            disabledContainerColor = Color(0xFF171A1F).copy(alpha = 0.3f),
                            disabledContentColor = textColor
                        ),
                        onClick = { MINavController.popBackStack() },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.close_24px),
                            contentDescription = "Unselected icon button."
                        )
                    }
                }
                Image (painterResource(R.drawable.finish_256), contentDescription = "", Modifier.size(400.dp))
                //Box() { Text("Здесь будет отобпажаться 3D-модель", fontFamily = fontFamily, color = textColor) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF171A1F).copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    TextButton(
                        onClick = {  }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.refresh_24px),
                                contentDescription = "",
                                tint = textColor
                            )
                            Text(
                                "Сброс",
                                fontFamily = fontFamily,
                                color = textColor
                            )
                        }
                    }
                    TextButton(
                        onClick = {  }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.photo_camera_24px),
                                contentDescription = "",
                                tint = textColor
                            )
                            Text(
                                "Снимок",
                                fontFamily = fontFamily,
                                color = textColor
                            )
                        }
                    }
                    TextButton(
                        onClick = {  }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.light_mode_24px),
                                contentDescription = "",
                                tint = textColor
                            )
                            Text(
                                "Свет",
                                fontFamily = fontFamily,
                                color = textColor
                            )
                        }
                    }
                    TextButton(
                        onClick = {  }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.wallpaper_24px),
                                contentDescription = "",
                                tint = textColor
                            )
                            Text(
                                "Фон",
                                fontFamily = fontFamily,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}