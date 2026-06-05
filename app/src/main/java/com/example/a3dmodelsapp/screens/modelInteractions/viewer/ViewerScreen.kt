package com.example.a3dmodelsapp.screens.modelInteractions.viewer

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FilterChipDefaults.filterChipBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.database.models.Model
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.primaryTransparent
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import com.google.android.filament.LightManager
import com.google.android.filament.Skybox
import com.google.android.filament.utils.KTX1Loader
import io.github.sceneview.SceneView
import io.github.sceneview.SurfaceType
import io.github.sceneview.environment.Environment
import io.github.sceneview.math.Direction
import io.github.sceneview.math.Position
import io.github.sceneview.math.colorOf
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberRenderer
import io.github.sceneview.rememberScene
import io.github.sceneview.rememberView
import io.github.sceneview.utils.readBuffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerScreen(MINavController: NavController, model: Model?) {

    val context = LocalContext.current
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    //LIGHT///////////////////////////////////////////////////////////////////////////////////////////////////
    val colors = listOf(
        Color(255, 201, 7),
        Color(255, 254, 242),
        Color(156, 204, 240),
    )

    data class LightTypeOption(val label: String, val type: LightManager.Type)

    val lightTypes = remember {
        listOf(
            LightTypeOption("Направленный", LightManager.Type.DIRECTIONAL),
            LightTypeOption("Точечный", LightManager.Type.POINT),
            LightTypeOption("Прожектор", LightManager.Type.FOCUSED_SPOT)
        )
    }

    val lightPosition = remember { Position(0f, 1.4f, 1.0f) }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    data class EnvOption(val label: String, val file: String)

    val environments = remember {
        listOf(
            EnvOption("Кухня", "envs/studio_2k.hdr"),
            EnvOption("Ванная", "envs/modern_bathroom_2k.hdr"),
            EnvOption("Гостинная", "envs/lythwood_lounge_2k.hdr"),
            EnvOption("Улица", "envs/chinese_garden_2k.hdr"),
            EnvOption("Белый фон", "envs/neutral/neutral_ibl.ktx"),
            EnvOption("Чёрный фон", "envs/neutral/neutral_ibl.ktx"),
        )
    }

    data class IntensityOption(val label: String, val lux: Float)

    val intensities = remember {
        listOf(
            IntensityOption("По умолчанию", 10_000f),
            IntensityOption("Ярко", 30_000f),
            IntensityOption("Тускло", 3_000f),
        )
    }

    val sheetState = rememberModalBottomSheetState()
    var showLightBottomSheet by remember { mutableStateOf(false) }
    var showBackgroundBottomSheet by remember { mutableStateOf(false) }

    val view = LocalView.current
    val activity = view.context as Activity

    // Начальные значения для сброса
    val defaultLightActive = false
    val defaultSelectedColor = colors[0]
    val defaultSelectedType = lightTypes[0]
    val defaultIntensity = 30_000f
    val defaultShowLightSource = false
    val defaultSelectedEnv = environments[0]
    val defaultSelectedIntensity = intensities[0]

    var selectedColor by remember { mutableStateOf(defaultSelectedColor) }
    var selectedType by remember { mutableStateOf(defaultSelectedType) }
    var intensity by remember { mutableFloatStateOf(defaultIntensity) }
    var showLightSource by remember { mutableStateOf(defaultShowLightSource) }
    var lightActive by remember { mutableStateOf(defaultLightActive) }
    var selectedEnv by remember { mutableStateOf(defaultSelectedEnv) }
    var selectedIntensity by remember { mutableStateOf(defaultSelectedIntensity) }

    val resetToDefault = {
        selectedColor = defaultSelectedColor
        selectedType = defaultSelectedType
        intensity = defaultIntensity
        showLightSource = defaultShowLightSource
        lightActive = defaultLightActive
        selectedEnv = defaultSelectedEnv
        selectedIntensity = defaultSelectedIntensity
    }

    val markerMaterial = rememberUnlitMaterialInstance(materialLoader, selectedColor)

    val environment: Environment = remember(environmentLoader, selectedEnv, selectedIntensity) {

        when (selectedEnv.label) {
            "Белый фон" ->
                environmentLoader.createEnvironment(
                    indirectLight = KTX1Loader.createIndirectLight(
                        engine,
                        context.assets.readBuffer("envs/neutral/neutral_ibl.ktx"),
                    ).indirectLight?.also { it.intensity = selectedIntensity.lux },
                    skybox = Skybox.Builder()
                        .color(colorOf(rgb = 1.0f, a = 1.0f).toFloatArray())
                        .build(engine),
                )

            "Чёрный фон" ->
                environmentLoader.createEnvironment(
                    indirectLight = KTX1Loader.createIndirectLight(
                        engine,
                        context.assets.readBuffer("envs/neutral/neutral_ibl.ktx"),
                    ).indirectLight?.also { it.intensity = selectedIntensity.lux },
                    skybox = Skybox.Builder()
                        .color(colorOf(rgb = 0.0f, a = 0.0f).toFloatArray())
                        .build(engine),
                )

            else -> environmentLoader.createHDREnvironment(
                assetFileLocation = selectedEnv.file,
                indirectLightApply = {
                    intensity(selectedIntensity.lux)
                },
                createSkybox = true
            ) ?: environmentLoader.createEnvironment()
        }
    }

    DisposableEffect(environment) {
        onDispose { environmentLoader.destroyEnvironment(environment) }
    }

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

    val modelUrl = model?.file_url

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


    Scaffold { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // Фон - 3D сцена
            SceneView(
                renderer = rememberRenderer(engine),
                modifier = Modifier.fillMaxSize(),
                engine = engine,
                view = rememberView(engine),
                scene = rememberScene(engine),
                modelLoader = modelLoader,
                materialLoader = materialLoader,
                environmentLoader = environmentLoader,
                environment = environment,
                mainLightNode = null,
                surfaceType = SurfaceType.TextureSurface,
            ) {

                modelInstanceState.value?.let { instance ->

                    ModelNode(
                        modelInstance = instance,
                        scaleToUnits = 1.0f,
                        autoAnimate = true
                    )
                }

                if (lightActive) {
                    if (selectedType.type != LightManager.Type.DIRECTIONAL) {
                        if (showLightSource) {
                            SphereNode(
                                materialInstance = markerMaterial,
                                radius = 0.09f,
                                position = lightPosition,
                            )
                        }
                    }
                    LightNode(
                        type = selectedType.type,
                        intensity = intensity,
                        color = colorOf(
                            r = selectedColor.red,
                            g = selectedColor.green,
                            b = selectedColor.blue
                        ),
                        direction = Direction(0f, -1.4f, -1.0f),
                        position = lightPosition,
                        apply = {
                            if (selectedType.type == LightManager.Type.FOCUSED_SPOT) {
                                spotLightCone(0.05f, 0.2f)
                                falloff(4f)
                            } else if (selectedType.type == LightManager.Type.POINT) {
                                falloff(2.5f)
                            }
                        }
                    )
                }

            }
//            SceneView(modifier = Modifier.fillMaxSize()) {
//                rememberModelInstance(modelLoader, "models/helmet.glb")?.let {
//                    ModelNode(modelInstance = it, scaleToUnits = 1.0f, autoAnimate = true)
//                }
//            }

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
                Box {
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
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                shape = CircleShape,
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
                            Row {
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
                                            "Освещение",
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
                                            "Окружение",
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
                                    onClick = { resetToDefault() },
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
                    sheetState = sheetState,
                    containerColor = backgroundColor,
                    contentColor = textColor,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "Включить свет",
                            style = CustomTextStyles.body1_regular,
                            fontFamily = fontFamily
                        )
                        Switch(
                            checked = lightActive,
                            onCheckedChange = { lightActive = it },
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = borderColor,
                                uncheckedTrackColor = secondary,
                                uncheckedBorderColor = borderColor,
                                checkedThumbColor = secondary
                            )
                        )
                        if (lightActive) {
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
                                            selectedContainerColor = primary,
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
                                        checkedThumbColor = secondary
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
                                                    Modifier.border(
                                                        3.dp,
                                                        MaterialTheme.colorScheme.primary,
                                                        CircleShape
                                                    )
                                                } else Modifier
                                            )
                                            .clickable { selectedColor = preset }
                                    )
                                }
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
                    sheetState = sheetState,
                    containerColor = backgroundColor,
                    contentColor = textColor,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
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
                                        selectedContainerColor = primary,
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
                                        selectedContainerColor = primary,
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
    url: String?
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