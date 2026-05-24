package com.example.a3dmodelsapp.screens.modelInteractions.viewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
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
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.gradient1
import com.example.a3dmodelsapp.ui.theme.gradient2
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.primaryTransparent
import com.example.a3dmodelsapp.ui.theme.textColor
import io.github.sceneview.SceneView
import io.github.sceneview.rememberModelInstance

import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.platform.LocalView
import android.app.Activity

@Composable
fun ViewerScreen(MINavController: NavController) {
    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Фон - 3D сцена
            SceneView(
                modifier = Modifier.fillMaxSize()
            ) {
                rememberModelInstance(modelLoader, "models/helmet.glb")?.let {
                    ModelNode(
                        modelInstance = it,
                        scaleToUnits = 1.0f,
                        autoAnimate = true
                    )
                }
            }
            Box() {

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
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            colors = IconButtonColors(
                                containerColor = backgroundColor.copy(alpha = 0.3f),
                                contentColor = textColor,
                                disabledContainerColor = backgroundColor.copy(alpha = 0.3f),
                                disabledContentColor = textColor
                            ),
                            onClick = { MINavController.popBackStack() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.close_24px),
                                contentDescription = "/"
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = backgroundColor.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = primary,
                                selectedTextColor = primary,
                                selectedIndicatorColor = primaryTransparent,
                                unselectedIconColor = textColor,
                                unselectedTextColor = textColor,
                                disabledIconColor = textColor,
                                disabledTextColor = textColor
                            ),
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.refresh_24px),
                                    contentDescription = "",
                                )
                            },
                            label = {
                                Text(
                                    "Сброс",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_regular
                                )
                            }
                        )
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = primary,
                                selectedTextColor = primary,
                                selectedIndicatorColor = primaryTransparent,
                                unselectedIconColor = textColor,
                                unselectedTextColor = textColor,
                                disabledIconColor = textColor,
                                disabledTextColor = textColor
                            ),
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.photo_camera_24px),
                                    contentDescription = "",
                                )
                            },
                            label = {
                                Text(
                                    "Снимок",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_regular
                                )
                            }
                        )
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = primary,
                                selectedTextColor = primary,
                                selectedIndicatorColor = primaryTransparent,
                                unselectedIconColor = textColor,
                                unselectedTextColor = textColor,
                                disabledIconColor = textColor,
                                disabledTextColor = textColor
                            ),
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.light_24px),
                                    contentDescription = "",
                                )
                            },
                            label = {
                                Text(
                                    "Свет",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_regular
                                )
                            }
                        )
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = primary,
                                selectedTextColor = primary,
                                selectedIndicatorColor = primaryTransparent,
                                unselectedIconColor = textColor,
                                unselectedTextColor = textColor,
                                disabledIconColor = textColor,
                                disabledTextColor = textColor
                            ),
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.wallpaper_24px),
                                    contentDescription = "",
                                )
                            },
                            label = {
                                Text(
                                    "Фон",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_regular
                                )
                            }
                        )
//                        TextButton(
//                            onClick = { }
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Icon(
//                                    painter = painterResource(R.drawable.refresh_24px),
//                                    contentDescription = "",
//                                    tint = textColor
//                                )
//                                Text(
//                                    "Сброс",
//                                    fontFamily = fontFamily,
//                                    color = textColor,
//                                    style = CustomTextStyles.body2_regular
//                                )
//                            }
//                        }
//                        TextButton(
//                            onClick = { }
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Icon(
//                                    painter = painterResource(R.drawable.photo_camera_24px),
//                                    contentDescription = "",
//                                    tint = textColor
//                                )
//                                Text(
//                                    "Снимок",
//                                    fontFamily = fontFamily,
//                                    color = textColor
//                                )
//                            }
//                        }
//                        TextButton(
//                            onClick = { }
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Icon(
//                                    painter = painterResource(R.drawable.light_24px),
//                                    contentDescription = "",
//                                    tint = textColor
//                                )
//                                Text(
//                                    "Свет",
//                                    fontFamily = fontFamily,
//                                    color = textColor
//                                )
//                            }
//                        }
//                        TextButton(
//                            onClick = { }
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Icon(
//                                    painter = painterResource(R.drawable.wallpaper_24px),
//                                    contentDescription = "",
//                                    tint = textColor
//                                )
//                                Text(
//                                    "Фон",
//                                    fontFamily = fontFamily,
//                                    color = textColor
//                                )
//                            }
//                        }
                    }
                }
            }
        }
    }


//    Scaffold() {innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .background(
//                    Brush.linearGradient(
//                        colors = listOf(gradient1, gradient2),
//                        start = Offset(0f, 0f)
//                    )
//                )
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(10.dp),
//                verticalArrangement = Arrangement.SpaceBetween,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Start
//                ) {
//                    IconButton(
//                        colors = IconButtonColors(
//                            containerColor = backgroundColor.copy(alpha = 0.3f),
//                            contentColor = textColor,
//                            disabledContainerColor = backgroundColor.copy(alpha = 0.3f),
//                            disabledContentColor = textColor
//                        ),
//                        onClick = { MINavController.popBackStack() },
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.close_24px),
//                            contentDescription = "Unselected icon button."
//                        )
//                    }
//                }
//                Image (painterResource(R.drawable.finish_256), contentDescription = "", Modifier.size(400.dp))
//                //Box() { Text("Здесь будет отобпажаться 3D-модель", fontFamily = fontFamily, color = textColor) }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(
//                            color = backgroundColor.copy(alpha = 0.3f),
//                            shape = RoundedCornerShape(20.dp)
//                        ),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//
//                    TextButton(
//                        onClick = {  }
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.refresh_24px),
//                                contentDescription = "",
//                                tint = textColor
//                            )
//                            Text(
//                                "Сброс",
//                                fontFamily = fontFamily,
//                                color = textColor
//                            )
//                        }
//                    }
//                    TextButton(
//                        onClick = {  }
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.photo_camera_24px),
//                                contentDescription = "",
//                                tint = textColor
//                            )
//                            Text(
//                                "Снимок",
//                                fontFamily = fontFamily,
//                                color = textColor
//                            )
//                        }
//                    }
//                    TextButton(
//                        onClick = {  }
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.light_24px),
//                                contentDescription = "",
//                                tint = textColor
//                            )
//                            Text(
//                                "Свет",
//                                fontFamily = fontFamily,
//                                color = textColor
//                            )
//                        }
//                    }
//                    TextButton(
//                        onClick = {  }
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.wallpaper_24px),
//                                contentDescription = "",
//                                tint = textColor
//                            )
//                            Text(
//                                "Фон",
//                                fontFamily = fontFamily,
//                                color = textColor
//                            )
//                        }
//                    }
//                }
//            }
//        }
    }
