package com.example.a3dmodelsapp.screens.info

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import com.example.a3dmodelsapp.ui.theme.borderColor

@Preview
@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            //elevation = 5.dp,
        ) {
            Box(modifier = Modifier.height(200.dp)) {
                Image(
                    painter = painterResource(R.drawable.image_is_ref_1),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 290f
                        )
                    ))
                Box(modifier = Modifier
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
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(21) {index ->
                        Badge(
                            containerColor = secondary,
                            contentColor = textColor
                        ) {
                            Text(
                                "Категория $index",
                                modifier = Modifier.padding(5.dp),
                                style = CustomTextStyles.body2_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }
            )
        }

        Text(
            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОпи" +
                    "саниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание.",
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
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        ModelInfo(painter = painterResource(R.drawable.description_24px), text = "Формат: GLTF")
                        ModelInfo(painter = painterResource(R.drawable.hard_drive_24px), text = "Размер: 12.5 МБ")
                    }
                    ModelInfo(painter = painterResource(R.drawable.signal_cellular_null_24px), text = "Количество полигонов: 250k")
                }
            }
        }
        Button(
            onClick = {},
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.visibility_24px),
                    contentDescription = "",
                    tint = backgroundColor
                )
                Text(
                    "Открыть в режиме 3D-просмотра",
                    fontFamily = fontFamily,
                    style = CustomTextStyles.body1_medium,
                    color = backgroundColor
                )
            }
        }
        OutlinedButton(
            onClick = { },
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
        OutlinedButton(
            onClick = {},
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.photo_24px),
                    contentDescription = "",
                    tint = textColor
                )
                Text(
                    "Разместить на фото",
                    fontFamily = fontFamily,
                    style = CustomTextStyles.body1_medium,
                    color = textColor
                )
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
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            Modifier.size(15.dp)
        )
        Text(
            text,
            fontFamily = fontFamily,
            style = CustomTextStyles.body2_regular,
            color = textColor
        )
    }
}
