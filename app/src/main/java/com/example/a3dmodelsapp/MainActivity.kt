package com.example.a3dmodelsapp

import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.ripple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a3dmodelsapp.database.ApiClient
import com.example.a3dmodelsapp.lazyColumn.LazyColumnTest
import com.example.a3dmodelsapp.screens.catalogue.CardGrid
import com.example.a3dmodelsapp.screens.catalogue.CatalogueScreen
import com.example.a3dmodelsapp.screens.info.InfoScreen
import com.example.a3dmodelsapp.screens.login.GetUserByLoginRequest
import com.example.a3dmodelsapp.screens.login.UserResponse
import com.example.a3dmodelsapp.screens.upload.UploadScreen
import com.example.a3dmodelsapp.screens.userInfo.UserInfoScreen
import com.example.a3dmodelsapp.screens.viewer.ViewerScreen
import com.example.a3dmodelsapp.ui.theme.MainColor
import com.example.a3dmodelsapp.ui.theme.SecondColor
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.test
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.viewModels.MainViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userLogin = intent.getStringExtra("USER_LOGIN") ?: "test1"
        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        sharedPreferences.edit {
            putString("user_login", userLogin)
        }



        setContent {

            val mainViewModel: MainViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(sharedPreferences, userLogin) as T
                    }
                }
            )

            _3DModelsAppTheme{
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
                                titleContentColor = primary,
                                actionIconContentColor = textColor,
                                subtitleContentColor = textColor
                            ),
                            navigationIcon = {
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        painterResource(id = R.drawable.arrow_back_24px),
                                        contentDescription = "/."
                                    )
                                }
                            },
                            title = {
                                Text(
                                    "Загрузка 3D-модели",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    //color = primary,
                                    fontFamily = fontFamily)
                            },
                            actions = {
//                                IconButton(onClick = { /* doSomething() */ }) {
//                                    Icon(
//                                        painterResource(id = R.drawable.upload_24px),
//                                        contentDescription = "",
//                                    )
//                                }
//                                IconButton(onClick = { /* doSomething() */ }) {
//                                    Icon(painterResource(id = R.drawable.search_24px), contentDescription = "")
//                                }
                            },
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier.drawBehind {
                                val strokeWidth = 4.dp.toPx()
                                drawLine(
                                    color = test,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = strokeWidth
                                )
                            },
                            containerColor = secondary,
                            contentColor = textColor,
                            content =
                                {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                        TextButton(
                                            onClick = {  }
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.view_cozy_24px),
                                                    contentDescription = "",
                                                    tint = textColor
                                                )
                                                Text("Каталог",
                                                    color = textColor,
                                                    fontFamily = fontFamily,
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
                                                    painter = painterResource(R.drawable.upload_24px),
                                                    contentDescription = "",
                                                    tint = primary
                                                )
                                                Text(
                                                    "Загрузка",
                                                    fontFamily = fontFamily,
                                                    color = primary
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
                                                    painter = painterResource(R.drawable.account_box_24px),
                                                    contentDescription = "",
                                                    tint = primary
                                                )
                                                Text(
                                                    "Аккаунт",
                                                    fontFamily = fontFamily,
                                                    color = primary
                                                )
                                            }
                                        }
                                    }
                                }
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        //ViewerScreen()
                        //InfoScreen()
                        //UploadScreen()
                        MainScreen(mainViewModel, sharedPreferences, userLogin)
                        //CardGrid()
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences,
    userLogin: String
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    UserInfoScreen(userLogin)
}

//@Composable
//fun AppHeader() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Text(
//            text="AndroidSprint",
//            fontSize = 28.sp,
//            //color = MainColor,
//            fontFamily = fontFamily
//        )
//        Text(
//            text="Изучение kOTLIN",
//            fontSize = 16.sp,
//            fontFamily = fontFamily
//            //color = SecondColor
//        )
//    }
//}
//
//@Composable
//fun Buttons() {
// Row(
//     modifier = Modifier.padding(horizontal = 5.dp)
// ) {
//     Button(
//         onClick={},
//         shape = RoundedCornerShape(13.dp),
//         modifier = Modifier
//             .weight(1f)
//             .padding(horizontal = 5.dp),
//     ){
//         Text("Раздел 1", fontFamily = fontFamily)
//     }
//     Button(
//         onClick={},
//         shape = RoundedCornerShape(13.dp),
//         modifier = Modifier
//             .weight(1f)
//             .padding(horizontal = 5.dp)
//     ){
//         Text("Раздел 2", fontFamily = fontFamily)
//     }
//     Button(
//         onClick={},
//         shape = RoundedCornerShape(13.dp),
//         modifier = Modifier
//             .weight(1f)
//             .padding(horizontal = 5.dp)
//     ){
//         Text("Раздел 3", fontFamily = fontFamily)
//     }
// }
//}
//
//@Composable
//fun NewImage() {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Image(
//            contentDescription = "",
//            painter = painterResource(R.drawable.sjosbbhylqde1),
//            modifier = Modifier
//                .size(120.dp)
//                .shadow(3.dp, CircleShape)
//                .clip(CircleShape)
//                .clickable(
//                    onClick = {},
//                )
//        )
//
//        Text(
//            text="Начать",
//            fontSize = 16.sp,
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewButtons() {
//Buttons()
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    AppHeader()
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ImagePreview() {
//    NewImage()
//}