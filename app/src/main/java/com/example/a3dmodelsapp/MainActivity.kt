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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a3dmodelsapp.screens.catalogue.CatalogueScreen
import com.example.a3dmodelsapp.screens.modelInteractions.ModelInteractionsNavigation
import com.example.a3dmodelsapp.screens.upload.UploadScreen
import com.example.a3dmodelsapp.screens.userInfo.UserInfoScreen
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme
import com.example.a3dmodelsapp.ui.theme.error
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.primaryTransparent
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import com.example.a3dmodelsapp.ui.theme.transparent
import com.example.a3dmodelsapp.viewModels.MainViewModel

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
            val view = LocalView.current

            LaunchedEffect(Unit) {
                val window = (view.context as Activity).window

                window.navigationBarColor =
                    secondary.toArgb()
            }

            val mainViewModel: MainViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(sharedPreferences, userLogin) as T
                    }
                }
            )
            _3DModelsAppTheme {
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

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 6.dp,
                        shape = RectangleShape,
                        clip = false
                    ),
                colors = TopAppBarColors(
                    containerColor = secondary,
                    scrolledContainerColor = secondary,
                    navigationIconContentColor = textColor,
                    titleContentColor = textColor,
                    actionIconContentColor = textColor,
                    subtitleContentColor = textColor
                ),
                navigationIcon = {
                    if (!mainViewModel.searchButtonState) {
                        if (currentRoute == "upload" || currentRoute == "profile") {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    painterResource(id = R.drawable.arrow_back_24px),
                                    contentDescription = "/."
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(primary)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.icon),
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(5.dp),
                                    contentDescription = "Logo"
                                )
                            }
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
                    if (mainViewModel.searchButtonState) {
                        TopSearchBar(mainViewModel)
                    } else {
                        Text(
                            text = topBarText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = CustomTextStyles.heading_small,
                            fontFamily = fontFamily
                        )
                    }
                },

                actions = {
                    if (currentRoute == "catalogue") {
                        IconButton(
                            onClick = {
                                if (!mainViewModel.searchButtonState) {
                                    mainViewModel.changeButtonState()

                                } else {
                                    mainViewModel.changeButtonState()
                                }
                            }
                        ) {
                            Icon(
                                painter = if (mainViewModel.searchButtonState) painterResource(
                                    id = R.drawable.close_24px
                                ) else painterResource(id = R.drawable.search_24px),
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
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
                if (userLogin.isNotEmpty()) {
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
                            if (mainViewModel.searchButtonState) {
                                mainViewModel.changeButtonState()

                            }
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
                }
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
                        if (mainViewModel.searchButtonState) {
                            mainViewModel.changeButtonState()

                        }
                        navController.navigate("profile") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.person_24px),
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
                    viewModel = mainViewModel,
                )
            }
            composable("upload") { UploadScreen(userLogin, mainViewModel, navController) }
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
            ModelInteractionsNavigation(
                rootNavController,
                mainViewModel.current_model,
                mainViewModel
            )
        }
    }
}

@Composable
fun TopSearchBar(viewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val searchText by viewModel.searchText.collectAsState()
    val focusManager = LocalFocusManager.current

    val brush = remember {
        Brush.linearGradient(
            colors = listOf(primary, error)
        )
    }

    val isInitialized = remember { mutableStateOf(false) }
    LaunchedEffect(isInitialized) {
        if (!isInitialized.value) {
            viewModel.requestSearchFocus()
            isInitialized.value = true
        }
    }

    TextField(
        singleLine = true,
        value = searchText,
        textStyle = TextStyle(
            brush = brush,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = transparent, // Фон при фокусе
            unfocusedContainerColor = transparent,
            disabledContainerColor = transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface, // Цвет текста
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface, // Цвет текста
            cursorColor = MaterialTheme.colorScheme.onSurface, // Цвет курсора
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        onValueChange = viewModel::onSearchTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(viewModel.focusRequester)
            .onFocusChanged { focusState ->
                viewModel.isFocused = focusState.isFocused
            },
        placeholder = {
            Text(
                "Поиск…",
                style = CustomTextStyles.body1_regular,
                color = textFieldTip,
                fontFamily = fontFamily
            )
        }
    )
}