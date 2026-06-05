package com.example.a3dmodelsapp.screens.modelInteractions.info

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material3.Text
import coil.compose.AsyncImage
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    rootNavController: NavController,
    MINavController: NavController,
    viewModel: MainViewModel
) {

    val id = viewModel.current_model?.id

    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    val categories by viewModel._categories.collectAsState()

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
                        style = CustomTextStyles.heading_small,
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = primary
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.height(200.dp)) {

                    AsyncImage(
                        model = viewModel.current_model?.image_url,
                        contentDescription = "Model Preview",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.FillWidth,
                        placeholder = painterResource(R.drawable.cube),
                        error = painterResource(R.drawable.cube)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        backgroundColor
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
                                viewModel.current_model?.name ?: "",
                                style = CustomTextStyles.sub_heading_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )

                            Text(
                                "Автор: ${viewModel.current_model?.user_login}",
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.category_24px),
                        contentDescription = "",
                        modifier = Modifier.size(18.dp),
                        tint = textColor
                    )
                    Text(
                        "Категории",
                        fontFamily = fontFamily,
                        style = CustomTextStyles.sub_heading_regular,
                        color = textColor
                    )
                }

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LinearProgressIndicator(
                            trackColor = secondary
                        )
                    }
                } else {
                    if (categories.isEmpty()) {
                        Badge(
                            containerColor = secondary,
                            contentColor = textColor,
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                "Категории не выбраны",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = CustomTextStyles.body2_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )
                        }
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(categories) { category ->
                                Badge(
                                    containerColor = secondary,
                                    contentColor = textColor,
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text(
                                        category.name,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        ),
                                        style = CustomTextStyles.body2_regular,
                                        color = textColor,
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Text(
                viewModel.current_model?.description ?: "",
                style = CustomTextStyles.body1_regular,
                color = textColor,
                fontFamily = fontFamily
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = secondary
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),  // ← увеличил отступ
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.info_24px),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp),  // ← было 15.dp, увеличил до 18.dp
                            tint = textColor
                        )
                        Text(
                            "Характеристики",
                            fontFamily = fontFamily,
                            style = CustomTextStyles.sub_heading_regular,
                            color = textColor
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                "Высота: ${viewModel.current_model?.height} м",
                                fontFamily = fontFamily,
                                style = CustomTextStyles.body2_regular,  // 12.sp
                                color = textColor
                            )
                            Text(
                                "Ширина: ${viewModel.current_model?.width} м",
                                fontFamily = fontFamily,
                                style = CustomTextStyles.body2_regular,  // 12.sp
                                color = textColor
                            )
                            Text(
                                "Длина: ${viewModel.current_model?.length} м",
                                fontFamily = fontFamily,
                                style = CustomTextStyles.body2_regular,  // 12.sp
                                color = textColor
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                                color = borderColor
                            )
                            ModelInfo(
                                painter = painterResource(R.drawable.hard_drive_24px),
                                text = "Размер: ${viewModel.current_model?.size} МБ"
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { MINavController.navigate("viewer") },
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(R.drawable.visibility_24px),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
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
            if (viewModel.current_model?.user_login == viewModel.login) {
                OutlinedButton(
                    onClick = { MINavController.navigate("update") },
                    shape = CircleShape,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, borderColor),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
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
