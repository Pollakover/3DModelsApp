package com.example.a3dmodelsapp.screens.modelInteractions.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.primaryTransparent
import com.example.a3dmodelsapp.ui.theme.textColor
import io.github.sceneview.SceneView
import io.github.sceneview.rememberModelInstance

import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.platform.LocalView
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FilterChipDefaults.filterChipBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import com.google.android.filament.LightManager
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironment
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberRenderer
import io.github.sceneview.rememberScene
import io.github.sceneview.rememberView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerScreen(MINavController: NavController) {

    val colors = listOf(
        Color(255, 201, 7),
        Color(255, 254, 242),
        Color(156, 204, 240),
    )

    var selectedColor by remember { mutableStateOf(colors[0]) }

    data class LightTypeOption(val label: String, val type: LightManager.Type)

    val lightTypes = remember {
        listOf(
            LightTypeOption("Направленный", LightManager.Type.DIRECTIONAL),
            LightTypeOption("Точечный", LightManager.Type.POINT),
            LightTypeOption("Прожектор", LightManager.Type.FOCUSED_SPOT)
        )
    }

    var selectedType by remember { mutableStateOf(lightTypes[0]) }

    data class EnvOption(val label: String, val file: String)

    val environments = remember {
        listOf(
            EnvOption("Кухня", "envs/studio_2k.hdr"),
            EnvOption("Ванная", "envs/studio_warm_2k.hdr"),
            EnvOption("Гостинная", "envs/outdoor_cloudy_2k.hdr"),
            EnvOption("Спальня", "envs/chinese_garden_2k.hdr"),
//            EnvOption("Sunset", "environments/sunset_2k.hdr"),
//            EnvOption("Rooftop Night", "environments/rooftop_night_2k.hdr"),
//            EnvOption("Night Sky", "environments/night_sky_2k.hdr")
        )
    }

    var selectedEnv by remember { mutableStateOf(environments[0]) }

    data class IntensityOption(val label: String, val lux: Float?)

    val intensities = remember {
        listOf(
            IntensityOption("По умолчанию", null),
            IntensityOption("Ярко", 30_000f),
            IntensityOption("Тускло", 3_000f),
        )
    }

    var selectedIntensity by remember { mutableStateOf(intensities[0]) }

    // Filament 3D Engine
    val engine = rememberEngine()

    // Asset loaders
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    val backgroundSheetState = rememberModalBottomSheetState()
    val lightSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showLightBottomSheet by remember { mutableStateOf(false) }
    var showBackgroundBottomSheet by remember { mutableStateOf(false) }

    var intensity by remember { mutableFloatStateOf(30_000f) }
    var showLightSource by remember { mutableStateOf(true) }

    val view = LocalView.current
    val activity = view.context as Activity

    DisposableEffect(Unit) {

        val window = activity.window

        val controller = WindowInsetsControllerCompat(window, view)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    val modelUrl = "http://192.168.1.6:8080/files/models/1780176061303.glb"

    // Состояние модели
    val modelInstanceState = remember {
        mutableStateOf<io.github.sceneview.model.ModelInstance?>(null)
    }

    var isLoading by remember { mutableStateOf(true) }

    // Загрузка модели
    LaunchedEffect(Unit) {

        isLoading = true

        try {

            val file = withContext(Dispatchers.IO) {
                downloadGlbFile(
                    context = activity,
                    url = modelUrl
                )
            }

            val instance = modelLoader.createModelInstance(file)

            modelInstanceState.value = instance

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }


    Scaffold() { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.padding()
        ) {

            // Фон - 3D сцена
            SceneView(
                modifier = Modifier.fillMaxSize(),

                engine = engine,
                view = rememberView(engine),
                renderer = rememberRenderer(engine),
                scene = rememberScene(engine),

                modelLoader = modelLoader,
                materialLoader = materialLoader,
                environmentLoader = environmentLoader,

                mainLightNode = rememberMainLightNode(engine) {
                    intensity = intensity
                },

                environment = rememberEnvironment(environmentLoader) {
                    environmentLoader.createHDREnvironment(
                        assetFileLocation = selectedEnv.file
                    )!!
                }

            ) {

                modelInstanceState.value?.let { instance ->

                    ModelNode(
                        modelInstance = instance,
                        scaleToUnits = 1.0f,
                        autoAnimate = true
                    )
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        androidx.compose.material3.CircularProgressIndicator(
                            color = primary
                        )

                        Text(
                            text = "Загрузка модели…",
                            color = textColor,
                            fontFamily = fontFamily,
                            style = CustomTextStyles.body1_regular,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        // 👇 КНОПКА НАЗАД ВНУТРИ ОВЕРЛЕЯ
                        IconButton(
                            onClick = { MINavController.popBackStack() },
                            modifier = Modifier.padding(top = 20.dp),
                            colors = IconButtonColors(
                                containerColor = backgroundColor.copy(alpha = 0.3f),
                                contentColor = textColor,
                                disabledContainerColor = Color.White.copy(alpha = 0.3f),
                                disabledContentColor = textColor
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.close_24px),
                                contentDescription = null
                            )
                        }
                    }
                }
            } else {
                Box() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            IconButton(
                                shape = RoundedCornerShape(20.dp),
                                colors = IconButtonColors(
                                    containerColor = backgroundColor.copy(alpha = 0.3f),
                                    contentColor = textColor,
                                    disabledContainerColor = Color.White.copy(alpha = 0.3f),
                                    disabledContentColor = textColor
                                ),
                                onClick = { MINavController.popBackStack() },
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.close_24px),
                                    contentDescription = null
                                )
                            }
                        }
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = backgroundColor.copy(alpha = 0.3f)
                            ),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Row() {
                                NavigationBarItem(
                                    colors = NavigationBarItemColors(
                                        selectedIconColor = primary,
                                        selectedTextColor = primary,
                                        selectedIndicatorColor = primaryTransparent,
                                        unselectedIconColor = textColor,
                                        unselectedTextColor = textColor,
                                        disabledIconColor = textColor,
                                        disabledTextColor = textColor
                                    ),
                                    selected = false,
                                    onClick = { showLightBottomSheet = true },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.light_24px),
                                            contentDescription = "",
                                        )
                                    },
                                    label = {
                                        Text(
                                            "Свет",
                                            fontFamily = fontFamily,
                                            style = CustomTextStyles.body2_regular
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    colors = NavigationBarItemColors(
                                        selectedIconColor = primary,
                                        selectedTextColor = primary,
                                        selectedIndicatorColor = primaryTransparent,
                                        unselectedIconColor = textColor,
                                        unselectedTextColor = textColor,
                                        disabledIconColor = textColor,
                                        disabledTextColor = textColor
                                    ),
                                    selected = false,
                                    onClick = { showBackgroundBottomSheet = true },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.wallpaper_24px),
                                            contentDescription = "",
                                        )
                                    },
                                    label = {
                                        Text(
                                            "Фон",
                                            fontFamily = fontFamily,
                                            style = CustomTextStyles.body2_regular
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    colors = NavigationBarItemColors(
                                        selectedIconColor = primary,
                                        selectedTextColor = primary,
                                        selectedIndicatorColor = primaryTransparent,
                                        unselectedIconColor = textColor,
                                        unselectedTextColor = textColor,
                                        disabledIconColor = textColor,
                                        disabledTextColor = textColor
                                    ),
                                    selected = false,
                                    onClick = {  },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.refresh_24px),
                                            contentDescription = "",
                                        )
                                    },
                                    label = {
                                        Text(
                                            "Сброс",
                                            fontFamily = fontFamily,
                                            style = CustomTextStyles.body2_regular
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    colors = NavigationBarItemColors(
                                        selectedIconColor = primary,
                                        selectedTextColor = primary,
                                        selectedIndicatorColor = primaryTransparent,
                                        unselectedIconColor = textColor,
                                        unselectedTextColor = textColor,
                                        disabledIconColor = textColor,
                                        disabledTextColor = textColor
                                    ),
                                    selected = false,
                                    onClick = { },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.photo_camera_24px),
                                            contentDescription = "",
                                        )
                                    },
                                    label = {
                                        Text(
                                            "Снимок",
                                            fontFamily = fontFamily,
                                            style = CustomTextStyles.body2_regular
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }


            if (showLightBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showLightBottomSheet = false
                    },
                    sheetState = lightSheetState,
                    containerColor = backgroundColor,
                    contentColor = textColor,
                ) {
                    Column(
                        modifier = Modifier
                            .padding( 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "Тип освещения",
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            lightTypes.forEach { lt ->
                                FilterChip(
                                    shape = CircleShape,
                                    border = filterChipBorder(
                                        borderColor = borderColor,
                                        enabled = true,
                                        selected = selectedType == lt
                                    ),
                                    selected = selectedType == lt,
                                    onClick = { selectedType = lt },
                                    label = {
                                                Text(
                                                    lt.label,
                                                    style = CustomTextStyles.body2_regular,
                                                    fontFamily = fontFamily
                                                )
                                            },
                                    colors = FilterChipDefaults.filterChipColors(
                                        labelColor = textFieldTip,
                                        disabledContainerColor = Color.Transparent,
                                        selectedContainerColor= primary,
                                    ),
                                )
                            }
                        }
                        Text(
                            "Яркость: ${intensity.toInt()}",
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                        Slider(
                            value = intensity,
                            onValueChange = { intensity = it },
                            valueRange = 1_000f..100_000f,
                            colors = SliderDefaults.colors(
                                inactiveTrackColor = secondary
                            ),
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Показывать источник света",
                                style = CustomTextStyles.body1_regular,
                                fontFamily = fontFamily
                            )
                            Switch(
                                checked = showLightSource,
                                onCheckedChange = { showLightSource = it },
                                colors = SwitchDefaults.colors(
                                    uncheckedThumbColor = borderColor,
                                    uncheckedTrackColor = secondary,
                                    uncheckedBorderColor = borderColor,
                                )
                            )
                        }
                        Text(
                            "Цвет",
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            colors.forEach { preset ->
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(preset, CircleShape)
                                        .then(
                                            if (selectedColor == preset) {
                                                Modifier.border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                            } else Modifier
                                        )
                                        .clickable { selectedColor = preset }
                                )
                            }
                        }
                    }
                }
            }
            if (showBackgroundBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBackgroundBottomSheet = false
                    },
                    sheetState = lightSheetState,
                    containerColor = backgroundColor,
                    contentColor = textColor,
                ) {
                    Column(
                        modifier = Modifier
                            .padding( 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "HDR Окружение",
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            environments.forEach { env ->
                                FilterChip(
                                    shape = CircleShape,
                                    border = filterChipBorder(
                                        borderColor = borderColor,
                                        enabled = true,
                                        selected = selectedEnv == env
                                    ),
                                    selected = selectedEnv == env,
                                    onClick = { selectedEnv = env },
                                    label = {
                                                Text(
                                                    env.label,
                                                    style = CustomTextStyles.body2_regular,
                                                    fontFamily = fontFamily
                                                )
                                            },
                                    colors = FilterChipDefaults.filterChipColors(
                                        labelColor = textFieldTip,
                                        disabledContainerColor = Color.Transparent,
                                        selectedContainerColor= primary,
                                    ),
                                )
                            }
                        }
                        Text(
                            "Яркость",
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            intensities.forEach { option ->
                                FilterChip(
                                    shape = CircleShape,
                                    border = filterChipBorder(
                                        borderColor = borderColor,
                                        enabled = true,
                                        selected = selectedIntensity == option
                                    ),
                                    selected = selectedIntensity == option,
                                    onClick = { selectedIntensity = option },
                                    label = {
                                        Text(
                                            option.label,
                                            style = CustomTextStyles.body2_regular,
                                            fontFamily = fontFamily
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        labelColor = textFieldTip,
                                        disabledContainerColor = Color.Transparent,
                                        selectedContainerColor= primary,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun downloadGlbFile(
    context: Context,
    url: String
): File {

    return withContext(Dispatchers.IO) {

        val connection = URL(url).openConnection()
        connection.connect()

        val input = connection.getInputStream()

        val file = File(
            context.cacheDir,
            "temp_model.glb"
        )

        file.outputStream().use { output ->
            input.copyTo(output)
        }

        input.close()

        file
    }
}
