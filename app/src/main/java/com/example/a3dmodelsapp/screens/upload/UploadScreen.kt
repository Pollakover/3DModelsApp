package com.example.a3dmodelsapp.screens.upload

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip

@Composable
fun UploadScreen() {
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable(onClick = {})
                .drawBehind {
                    drawRoundRect(
                        color = borderColor,
                        style = Stroke(width = 10f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) ),
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
                //.border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
                //.background(secondary)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.upload_24px),
                contentDescription = "",
                tint = primary,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Выберите файл для загрузки",
                style = CustomTextStyles.body1_semi_bold,
                fontFamily = fontFamily,
                color = textColor
            )
            Text(
                text = "Поддерживается формат .gltf",
                style = CustomTextStyles.body2_regular,
                fontFamily = fontFamily,
                color = textColor
            )
        }
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
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
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
                    if (newText.length <= 25) {
                        desc = newText
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
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
        Button(
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                "Загрузить",
                fontFamily = fontFamily,
                style = CustomTextStyles.body1_medium,
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    _3DModelsAppTheme{
        UploadScreen()
    }
}