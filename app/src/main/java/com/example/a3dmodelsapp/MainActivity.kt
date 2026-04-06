package com.example.a3dmodelsapp

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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
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
import com.example.a3dmodelsapp.lazyColumn.LazyColumnTest
import com.example.a3dmodelsapp.screens.catalogue.CardGrid
import com.example.a3dmodelsapp.screens.catalogue.CatalogueScreen
import com.example.a3dmodelsapp.screens.info.InfoScreen
import com.example.a3dmodelsapp.screens.upload.UploadScreen
import com.example.a3dmodelsapp.ui.theme.MainColor
import com.example.a3dmodelsapp.ui.theme.SecondColor
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.test
import com.example.a3dmodelsapp.ui.theme.textColor

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                            title = {
                                Text(
                                    "Каталог моделей",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = primary,
                                    fontFamily = fontFamily)
                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(
                                        painterResource(id = R.drawable.upload_24px),
                                        contentDescription = "",
                                    )
                                }
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(painterResource(id = R.drawable.search_24px), contentDescription = "")
                                }
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
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { }
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.view_cozy_24px),
                                                contentDescription = "",
                                                tint = primary
                                            )
                                            Text("Каталог",
                                                color = primary,
                                                fontFamily = fontFamily,
                                            )
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { }
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.upload_24px),
                                                contentDescription = "",
                                            )
                                            Text(
                                                "Загрузка",
                                                fontFamily = fontFamily,
                                            )
                                        }
                                    }
                                }
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        InfoScreen()
                        //UploadScreen()
                        //CardGrid()
                    }
                }
            }

        }
    }
}

@Composable
fun AppHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text="AndroidSprint",
            fontSize = 28.sp,
            //color = MainColor,
            fontFamily = fontFamily
        )
        Text(
            text="Изучение kOTLIN",
            fontSize = 16.sp,
            fontFamily = fontFamily
            //color = SecondColor
        )
    }
}

@Composable
fun Buttons() {
 Row(
     modifier = Modifier.padding(horizontal = 5.dp)
 ) {
     Button(
         onClick={},
         shape = RoundedCornerShape(13.dp),
         modifier = Modifier
             .weight(1f)
             .padding(horizontal = 5.dp),
     ){
         Text("Раздел 1", fontFamily = fontFamily)
     }
     Button(
         onClick={},
         shape = RoundedCornerShape(13.dp),
         modifier = Modifier
             .weight(1f)
             .padding(horizontal = 5.dp)
     ){
         Text("Раздел 2", fontFamily = fontFamily)
     }
     Button(
         onClick={},
         shape = RoundedCornerShape(13.dp),
         modifier = Modifier
             .weight(1f)
             .padding(horizontal = 5.dp)
     ){
         Text("Раздел 3", fontFamily = fontFamily)
     }
 }
}

@Composable
fun NewImage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            contentDescription = "",
            painter = painterResource(R.drawable.sjosbbhylqde1),
            modifier = Modifier
                .size(120.dp)
                .shadow(3.dp, CircleShape)
                .clip(CircleShape)
                .clickable(
                    onClick = {},
                )
        )

        Text(
            text="Начать",
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButtons() {
Buttons()
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppHeader()
}

@Preview(showBackground = true)
@Composable
private fun ImagePreview() {
    NewImage()
}