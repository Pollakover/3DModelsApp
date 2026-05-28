package com.example.a3dmodelsapp.screens.modelInteractions.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.secondary

data class CategorySection(
    val title: String,
    val items: List<String>
)

@Composable
fun FurnitureCategories() {

    val sections = listOf(

        CategorySection(
            "Помещение",
            listOf(
                "Гостиная",
                "Спальня",
                "Кухня",
                "Ванная",
                "Офис",
                "Детская",
                "Прихожая"
            )
        ),

        CategorySection(
            "Тип",
            listOf(
                "Уличная мебель",
                "Мягкая мебель",
                "Корпусная мебель",
                "Модульная мебель"
            )
        ),

        CategorySection(
            "Категория",
            listOf(
                "Столы",
                "Стулья",
                "Диваны",
                "Кровати",
                "Шкафы",
                "Полки",
                "Светильники",
                "Тумбы",
                "Декор"
            )
        ),

        CategorySection(
            "Материал",
            listOf(
                "Дерево",
                "Металл",
                "Стекло",
                "Пластик",
                "Ткань",
                "Кожа"
            )
        ),

        CategorySection(
            "Цвет",
            listOf(
                "Белый",
                "Чёрный",
                "Серый",
                "Бежевый"
            )
        )
    )

    val checkedItems = remember {
        mutableStateMapOf<String, Boolean>()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
            .verticalScroll(rememberScrollState())
            .background(secondary)
            .padding(vertical = 8.dp)
    ) {

        sections.forEach { section ->

            Surface(
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = section.title,
                    style = CustomTextStyles.body1_bold,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
                )
            }

            section.items.forEach { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = item,
                        style = CustomTextStyles.body1_regular,
                        fontFamily = fontFamily
                    )

                    Checkbox(
                        checked = checkedItems[item] ?: false,
                        onCheckedChange = {
                            checkedItems[item] = it
                        }
                    )
                }
            }
        }
    }
}