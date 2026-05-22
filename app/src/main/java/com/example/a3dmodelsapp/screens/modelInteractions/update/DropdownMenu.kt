package com.example.a3dmodelsapp.screens.modelInteractions.update

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    selectedField: String,
    onFieldSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categories = listOf("В пути", "Доставлен", "Отменен", "Задерживается")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
            //.clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
    ) {
        TextField(
            //modifier = Modifier.fillMaxWidth(),
            value = selectedField,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                //Icon(painterResource(R.drawable.upload_24px), contentDescription = "")
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                           },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
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
            categories.forEach { status ->
                DropdownMenuItem(
                    text = {
                        Text(
                            fontFamily = fontFamily,
                            color = textColor,
                            style = CustomTextStyles.body1_regular,
                            text = status,
                        )
                    },
                    onClick = {
                        onFieldSelected(status)
                        expanded = false
                    }
                )
            }
        }
    }
}