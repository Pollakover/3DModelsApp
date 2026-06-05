package com.example.a3dmodelsapp.screens.modelInteractions.update

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import com.example.a3dmodelsapp.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    MINavController: NavController,
    rootNavController: NavController,
    viewModel: MainViewModel
) {
    val deleteDialogState = remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(viewModel.current_model?.name ?: "") }

    var desc by remember { mutableStateOf(viewModel.current_model?.description ?: "") }

    val categories by viewModel._categories.collectAsState()
    val selectedCategories = remember(categories) {
        mutableStateListOf(*categories.map { it.name }.toTypedArray())
    }

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
                    IconButton(onClick = { MINavController.popBackStack() }) {
                        Icon(
                            painterResource(id = R.drawable.arrow_back_24px),
                            contentDescription = "/."
                        )
                    }
                },
                title = {
                    Text(
                        text = "Редактирование",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CustomTextStyles.heading_small,
                        fontFamily = fontFamily
                    )
                },
            )
        },
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
        ) {
            Column(
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
                            .border(
                                1.dp,
                                color = borderColor,
                                CircleShape,
                            )
                            .padding(14.dp, 10.dp, 14.dp, 10.dp),
                        textStyle = TextStyle(
                            color = textColor,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
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
                            .border(
                                1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(14.dp, 10.dp, 14.dp, 10.dp),
                        textStyle = TextStyle(
                            color = textColor,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = fontFamily
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
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
                        selectedCategories = selectedCategories,

                        onCategoryCheckedChange = { category, isChecked ->

                            if (isChecked) {

                                if (!selectedCategories.contains(category)) {
                                    selectedCategories.add(category)
                                }

                            } else {

                                selectedCategories.remove(category)
                            }
                        }
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        items(selectedCategories) { category ->

                            Badge(
                                containerColor = secondary,
                                contentColor = textColor,
                                modifier = Modifier
                                    .height(32.dp)
                                    .clip(CircleShape)
                                    .clickable(
                                        onClick = {
                                            selectedCategories.remove(category)
                                        },
                                    )
                            ) {

                                Text(
                                    text = category,

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
                Button(
                    onClick = {
                        viewModel.updateModel(
                            name = name,
                            description = desc,
                            categories = selectedCategories.toList(),
                            onSuccess = {
                                MINavController.popBackStack()
                            }
                        )
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

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
            FloatingActionButton(
                onClick = { deleteDialogState.value = true },
                shape = RoundedCornerShape(20.dp),
                containerColor = primary,
                contentColor = backgroundColor,
            ) {
                Icon(
                    painterResource(R.drawable.delete_24px),
                    contentDescription = "Очистить поиск",
                    tint = backgroundColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (deleteDialogState.value) {
                DeleteDialog(
                    state = deleteDialogState,
                    viewModel = viewModel,
                    rootNavController = rootNavController
                )
            }
        }

    }
}

@Composable
fun DeleteDialog(
    state: MutableState<Boolean>,
    viewModel: MainViewModel,
    rootNavController: NavController
) {
    val isLoading by viewModel.isLoading.collectAsState()
    LocalContext.current

    if (state.value) {
        Dialog(onDismissRequest = { state.value = false }) {
            Card(
                modifier = Modifier.width(320.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = secondary),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                painterResource(R.drawable.delete_24px),
                                contentDescription = "",
                                tint = primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Text(
                            text = "Вы уверены, что хотите удалить 3D-модель?",
                            modifier = Modifier.fillMaxWidth(),
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                            TextButton(
                                onClick = {
                                    viewModel.deleteModel(
                                        onSuccess = {
                                            state.value = false

                                            rootNavController.navigate("main") {
                                                popUpTo("main") { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = primary,
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Text(
                                    text = "Да",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body1_bold,
                                )
                            }

                            TextButton(
                                onClick = { state.value = false },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = primary,
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Text(
                                    text = "Нет",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body1_bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
