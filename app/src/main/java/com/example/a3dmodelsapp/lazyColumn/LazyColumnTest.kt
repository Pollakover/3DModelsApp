package com.example.a3dmodelsapp.lazyColumn

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LazyColumnTest(paddingValues: PaddingValues) {

    val lessons = listOf("Урок1","Урок 2", "Урок 3")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        stickyHeader {
            Text(
                "Заголовок Раздела",
                modifier = Modifier.padding(8.dp).background(Color.LightGray).fillMaxWidth()
            )
        }

        itemsIndexed(lessons) { index, lesson ->
            Text(
                text = "${index+1}. $lesson",
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp
            )
        }

        items(50) { index ->
            Text(
                "${index+1}",
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp
            )
        }
    }
}