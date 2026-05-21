package com.example.a3dmodelsapp.screens.modelInteractions.info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.test

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(rootNavController: NavController, MINavController: NavController) {
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
                    IconButton(onClick = { rootNavController.popBackStack() }) {
                        Icon(
                            painterResource(id = R.drawable.arrow_back_24px),
                            contentDescription = "/."
                        )
                    }
                },
                title = {
                    Text(
                        text = "Информация о модели",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CustomTextStyles.sub_heading_regular,
                        fontFamily = fontFamily
                    )
                },
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)

        ) {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.height(200.dp)) {

                    Image(
                        painter = painterResource(R.drawable.image_is_ref_1),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 290f
                                )
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {

                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {

                            Text(
                                "Название",
                                style = CustomTextStyles.sub_heading_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )

                            Text(
                                "Автор: Имя автора",
                                style = CustomTextStyles.body2_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                Text(
                    "Категории",
                    style = CustomTextStyles.sub_heading_regular,
                    color = textColor,
                    fontFamily = fontFamily
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    items(21) { index ->

                        Badge(
                            containerColor = secondary,
                            contentColor = textColor,
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                "Категория $index",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = CustomTextStyles.body2_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }
            }

            Text(
                "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание.",
                style = CustomTextStyles.body1_regular,
                color = textColor,
                fontFamily = fontFamily
            )

            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(secondary)
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)  // ← было 10.dp, увеличил для воздушности
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)  // ← было 10.dp
                ) {

                    Text(
                        "Детали модели",
                        style = CustomTextStyles.sub_heading_regular,
                        color = textColor,
                        fontFamily = fontFamily
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {

                            ModelInfo(
                                painter = painterResource(R.drawable.description_24px),
                                text = "Формат: GLB"
                            )

                            ModelInfo(
                                painter = painterResource(R.drawable.hard_drive_24px),
                                text = "Размер: 12.5 МБ"
                            )
                        }

                        ModelInfo(
                            painter = painterResource(R.drawable.signal_cellular_null_24px),
                            text = "Количество полигонов: 250k"
                        )
                    }
                }
            }

            Button(
                onClick = {MINavController.navigate("viewer")},
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth()

            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(R.drawable.visibility_24px),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),  // ← оптимальный размер для иконок в кнопках
                        tint = backgroundColor
                    )

                    Text(
                        "Открыть в режиме 3D-просмотра",
                        fontFamily = fontFamily,
                        style = CustomTextStyles.body1_medium,
                        color = backgroundColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            OutlinedButton(
                onClick = { MINavController.navigate("update") },
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, borderColor)
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(R.drawable.edit_24px),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = textColor
                    )

                    Text(
                        "Изменить данные",
                        fontFamily = fontFamily,
                        style = CustomTextStyles.body1_medium,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun ModelInfo(
    painter: Painter,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),  // ← увеличил отступ
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(18.dp),  // ← было 15.dp, увеличил до 18.dp
            tint = textColor
        )
        Text(
            text,
            fontFamily = fontFamily,
            style = CustomTextStyles.body2_regular,  // 12.sp
            color = textColor
        )
    }
}
