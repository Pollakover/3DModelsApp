package com.example.a3dmodelsapp.screens.modelInteractions.update

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    selectedCategories: List<String>,
    onCategoryCheckedChange: (String, Boolean) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
    ) {

        TextField(
            value = if (selectedCategories.isEmpty())
                "Выберите категории"
            else
                "Выбрано: ${selectedCategories.size}",

            onValueChange = {},
            readOnly = true,

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },

            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                focusedTrailingIconColor = textColor,
                unfocusedTrailingIconColor = textColor
            ),

            textStyle = CustomTextStyles.body1_regular
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

            shape = RoundedCornerShape(20.dp),

            modifier = Modifier
                .border(1.dp, borderColor, RoundedCornerShape(20.dp))
                .background(secondary)
        ) {

            FurnitureCategories(
                selectedCategories = selectedCategories,
                onCategoryCheckedChange = onCategoryCheckedChange
            )
        }
    }
}