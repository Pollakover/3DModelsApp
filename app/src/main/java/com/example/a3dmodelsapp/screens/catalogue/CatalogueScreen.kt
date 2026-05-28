package com.example.a3dmodelsapp.screens.catalogue


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.test
import com.example.a3dmodelsapp.ui.theme.textColor

@Composable
fun ModelCard(name: String, painter: Painter, onOpenInfo: () -> Unit) {
    Card(

        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondary
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = { onOpenInfo()
            }),
        ) {
            Image(
                painter = painterResource(R.drawable.cube),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color.Transparent),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = name,
                modifier = Modifier.padding(10.dp),
                fontFamily = fontFamily,
                style = CustomTextStyles.body1_medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

val names = listOf("Стул", "Тумбочка", "Полка", "Ваза", "Коврик для гостинной с длинным ворсом")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueScreen(onOpenInfo: () -> Unit) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(20.dp),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 20.dp,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            items(names) { name ->
                ModelCard(name, painterResource(R.drawable.upload_24px), { onOpenInfo() })
            }
        }
    )
}