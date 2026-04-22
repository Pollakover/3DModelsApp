package com.example.a3dmodelsapp.screens.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.textColor

@Preview
@Composable
fun EditorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = "",
            painter = painterResource(R.drawable.interior),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                //.size(120.dp)
                .shadow(3.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
        )
//        Image(
//            painter = painterResource(R.drawable.image_is_ref_1),
//            contentDescription = "Logo",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize(),
//        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF171A1F).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            TextButton(
                onClick = {  }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.upload_24px),
                        contentDescription = "",
                        tint = textColor
                    )
                    Text(
                        "Загрузить",
                        fontFamily = fontFamily,
                        color = textColor
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
                        painter = painterResource(R.drawable.save_24px),
                        contentDescription = "",
                        tint = textColor
                    )
                    Text(
                        "Сохранить",
                        fontFamily = fontFamily,
                        color = textColor
                    )
                }
            }
        }
    }
}