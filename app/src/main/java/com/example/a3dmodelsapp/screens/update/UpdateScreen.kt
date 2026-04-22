package com.example.a3dmodelsapp.screens.update

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip

@Composable
fun UpdateScreen() {
    var name by remember { mutableStateOf("Название модели") }
    var desc by remember { mutableStateOf("Описание") }
    var status by remember { mutableStateOf("Категория 1") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Название модели",
                fontFamily = fontFamily,
                style = CustomTextStyles.body2_medium
            )

            BasicTextField(
                value = name,
                onValueChange = { newText ->
                    if (newText.length <= 25) {
                        name = newText
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    //.clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        color = borderColor,
                        CircleShape,
                        //shape = RoundedCornerShape(8.dp)
                    )
                    .padding(14.dp, 10.dp, 14.dp, 10.dp),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontFamily
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (name.isEmpty()) {
                            Text(
                                text = "Введите название вашей 3D-модели",
                                fontFamily = fontFamily,
                                color = textFieldTip,
                                style = CustomTextStyles.body1_regular
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Описание",
                fontFamily = fontFamily,
                ////color = MaterialTheme.colorScheme.onBackground,
                style = CustomTextStyles.body2_medium
            )

            BasicTextField(
                value = desc,
                onValueChange = { newText ->
                    if (newText.length <= 200) {
                        desc = newText
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    //.clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        color = borderColor,
                        //CircleShape,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(14.dp, 10.dp, 14.dp, 10.dp),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontFamily
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        //contentAlignment = Alignment.CenterStart
                    ) {
                        if (desc.isEmpty()) {
                            Text(
                                text = "Опишите вашу 3D-модель",
                                fontFamily = fontFamily,
                                color = textFieldTip,
                                style = CustomTextStyles.body1_regular
                            )
                        }
                        innerTextField()
                    }
                },
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Категории",
                fontFamily = fontFamily,
                style = CustomTextStyles.body2_medium
            )
            DropdownMenu(
                selectedField = status,
                onFieldSelected = { newStatus ->
                    status = newStatus
                },
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
                    painter = painterResource(R.drawable.save_24px),
                    contentDescription = "",
                    tint = backgroundColor
                )
                Text(
                    "Сохранить изменения",
                    fontFamily = fontFamily,
                    style = CustomTextStyles.body1_medium,
                    color = backgroundColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    _3DModelsAppTheme{
        UpdateScreen()
    }
}