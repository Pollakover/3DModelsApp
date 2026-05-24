package com.example.a3dmodelsapp

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a3dmodelsapp.screens.catalogue.CatalogueScreen
import com.example.a3dmodelsapp.screens.modelInteractions.info.InfoScreen
import com.example.a3dmodelsapp.screens.upload.UploadScreen
import com.example.a3dmodelsapp.screens.userInfo.UserInfoScreen
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.test
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.viewModels.MainViewModel
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a3dmodelsapp.screens.modelInteractions.ModelInteractionsNavigation
import com.example.a3dmodelsapp.screens.modelInteractions.update.UpdateScreen
import com.example.a3dmodelsapp.screens.modelInteractions.viewer.ViewerScreen
import com.example.a3dmodelsapp.ui.theme.primaryTransparent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userLogin = intent.getStringExtra("USER_LOGIN") ?: ""
        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        sharedPreferences.edit {
            putString("user_login", userLogin)
        }

        setContent {
//            val view = LocalView.current
//
//            LaunchedEffect(Unit) {
//                val window = (view.context as Activity).window
//
//                window.navigationBarColor =
//                    secondary.toArgb()
//            }

            val mainViewModel: MainViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(sharedPreferences, userLogin) as T
                    }
                }
            )
            _3DModelsAppTheme() {
                RootNavigation(
                    mainViewModel,
                    sharedPreferences,
                    userLogin
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    rootNavController: NavController,
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences,
    userLogin: String
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    UserInfoScreen(userLogin)

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.drawBehind {
                    val strokeWidth = 4.dp.toPx()
                    drawLine(
                        color = test,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                },
                colors = TopAppBarColors(
                    containerColor = secondary,
                    scrolledContainerColor = secondary,
                    navigationIconContentColor = textColor,
                    titleContentColor = textColor,
                    actionIconContentColor = textColor,
                    subtitleContentColor = textColor
                ),
                navigationIcon = {
                    if (currentRoute == "upload" || currentRoute == "profile") {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painterResource(id = R.drawable.arrow_back_24px),
                                contentDescription = "/."
                            )
                        }
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(primary)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.icon),
                                modifier = Modifier.size(30.dp).padding(5.dp),
                                contentDescription = "Logo"
                            )
                        }
                    }
                },
                title = {
                    val topBarText = when (currentRoute) {
                        "catalogue" -> "Каталог 3D-моделей"
                        "upload" -> "Загрузка 3D-модели"
                        "profile" -> "Профиль"
                        else -> ""
                    }
                    Text(
                        text = topBarText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CustomTextStyles.heading_small,
                        fontFamily = fontFamily
                    )
                },

                actions = {
                    if (currentRoute == "catalogue") {
                        IconButton(onClick = {
                            navController.navigate("upload") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                painterResource(id = R.drawable.upload_24px),
                                contentDescription = "",
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painterResource(id = R.drawable.search_24px),
                                contentDescription = ""
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 4.dp.toPx()
                        drawLine(
                            color = test,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                    },
                containerColor = secondary,
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
                    selected = currentRoute == "catalogue",
                    onClick = {
                        navController.navigate("catalogue") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.view_cozy_24px),
                            contentDescription = "",
                        )
                    },
                    label = {
                        Text(
                            "Каталог",
                            fontFamily = fontFamily,
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
                    selected = currentRoute == "upload",
                    onClick = {
                        navController.navigate("upload") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.upload_24px),
                            contentDescription = "",
                        )
                    },
                    label = {
                        Text(
                            "Загрузка",
                            fontFamily = fontFamily,
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
                    selected = currentRoute == "profile",
                    onClick = {
                        navController.navigate("profile") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.account_box_24px),
                            contentDescription = "",
                        )
                    },
                    label = {
                        Text(
                            "Профиль",
                            fontFamily = fontFamily,
                        )
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "catalogue",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("catalogue") {
                CatalogueScreen(
                    onOpenInfo = {
                        rootNavController.navigate("info")
                    },
                )
            }
            composable("upload") { UploadScreen(userLogin) }
            composable("profile") { UserInfoScreen(userLogin) }
        }
    }
}

@Composable
fun RootNavigation(
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences,
    userLogin: String
) {

    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = "main"
    ) {

        composable("main") {
            MainScreen(
                rootNavController = rootNavController,
                mainViewModel = mainViewModel,
                sharedPreferences = sharedPreferences,
                userLogin = userLogin
            )
        }

        composable("info") {
            ModelInteractionsNavigation(rootNavController)
        }
    }
}