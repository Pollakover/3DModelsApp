package com.example.a3dmodelsapp.screens.upload

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.database.ApiClient
import com.example.a3dmodelsapp.database.models.UploadResponse
import com.example.a3dmodelsapp.screens.modelInteractions.update.DropdownMenu
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import com.example.a3dmodelsapp.viewModels.MainViewModel
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun UploadScreen(userLogin: String, viewModel: MainViewModel, navController: NavController) {

    FilamentLoader.init()

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var info by remember {
        mutableStateOf<ModelInfo?>(null)
    }

    val selectedCategories = remember {
        mutableStateListOf<String>()
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var model_id: Int by remember {
        mutableIntStateOf(0)
    }

    var isUploading by remember {
        mutableStateOf(false)
    }

    var name by remember { mutableStateOf("") }

    var desc by remember { mutableStateOf("") }

    // File Picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            val (isValid, error) =
                isValidGlbFile(context, uri)

            if (!isValid) {

                Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_LONG
                ).show()

                return@rememberLauncherForActivityResult
            }

            selectedUri = uri

            Toast.makeText(
                context,
                "Файл выбран",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(selectedUri) {

        val uri = selectedUri ?: return@LaunchedEffect

        withContext(Dispatchers.Default) {

            val renderer = OffscreenGlbRenderer(context)

            bitmap = renderer.renderGlbToBitmap(
                uri,
                1024,
                1024,
                -45f
            )

            info = renderer.getModelInfo(uri)

            renderer.destroy()

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {
                    launcher.launch("*/*")
                }
                .fillMaxWidth()
                .height(200.dp)
                .drawBehind {

                    drawRoundRect(
                        color = borderColor,

                        style = Stroke(
                            width = 10f,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(10f, 10f),
                                0f
                            )
                        ),

                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .padding(0.dp),
        ) {
            if (bitmap != null) {
                bitmap?.let {

                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.upload_24px),
                        contentDescription = "",
                        tint = primary,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        "Выберите файл для загрузки",
                        style = CustomTextStyles.body1_semi_bold,
                        fontFamily = fontFamily,
                        color = textColor
                    )
                    Text(
                        "Формат файла должен быть .glb",
                        style = CustomTextStyles.body2_regular,
                        fontFamily = fontFamily,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        "Размер не должен превышать 50 МБ",
                        style = CustomTextStyles.body2_regular,
                        fontFamily = fontFamily,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Название",
                fontFamily = fontFamily,
                style = CustomTextStyles.body2_medium
            )

            BasicTextField(
                singleLine = true,
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
                    color = MaterialTheme.colorScheme.onSurface,
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
                    color = MaterialTheme.colorScheme.onSurface,
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

        if (isUploading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LinearProgressIndicator(
                    trackColor = secondary
                )
            }
        }

        Button(
            onClick = {
                if (selectedUri == null) {
                    Toast.makeText(
                        context,
                        "Файл не выбран",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                } else {
                    if (name.isEmpty() || desc.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Заполните все поля",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                }

                val uri = selectedUri ?: return@Button

                val (isValid, error) =
                    isValidGlbFile(context, uri)

                if (!isValid) {

                    Toast.makeText(
                        context,
                        error,
                        Toast.LENGTH_LONG
                    ).show()

                    return@Button
                }

                scope.launch {

                    isUploading = true

                    try {

                        val result = uploadModel(
                            context = context,
                            uri = selectedUri!!,
                            info = info,
                            name = name,
                            description = desc,
                            userLogin = userLogin,
                            bitmap = bitmap
                        )

                        model_id = result?.id ?: 0

                        selectedCategories.forEach { category ->
                            viewModel.addCategory(
                                model_id = model_id,
                                name = category
                            )
                        }

                        Toast.makeText(
                            context,
                            "Файл загружен",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()

                    } catch (e: Exception) {

                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                        e.message?.let { Log.e("ERROR", it) }
                    }

                    isUploading = false
                }
            },

            shape = CircleShape,
            modifier = Modifier.fillMaxWidth(),
            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.upload_24px),
                    contentDescription = "",
                    tint = backgroundColor
                )

                Text(
                    if (isUploading)
                        "Загрузка…"
                    else
                        "Загрузить",

                    fontFamily = fontFamily,

                    style = CustomTextStyles.body1_medium,

                    color = backgroundColor
                )
            }
        }

    }
}

suspend fun uploadModel(

    context: Context,
    uri: Uri,
    info: ModelInfo?,

    name: String,
    description: String,
    userLogin: String,
    bitmap: Bitmap?

): UploadResponse? {

    val contentResolver = context.contentResolver

    val inputStream =
        contentResolver.openInputStream(uri)

    val fileBytes =
        inputStream?.readBytes() ?: return null

    val requestFile =
        fileBytes.toRequestBody(
            "application/octet-stream".toMediaType()
        )

    val filePart =
        MultipartBody.Part.createFormData(
            "file",
            "model.glb",
            requestFile
        )

    val stream = ByteArrayOutputStream()

    bitmap?.compress(
        Bitmap.CompressFormat.PNG,
        100,
        stream
    )

    val pngBytes = stream.toByteArray()

    val previewRequestBody =
        pngBytes.toRequestBody("image/png".toMediaType())

    val previewPart =
        MultipartBody.Part.createFormData(
            "preview",
            "preview.png",
            previewRequestBody
        )

    val nameBody =
        name.toRequestBody("text/plain".toMediaType())

    val descBody =
        description.toRequestBody("text/plain".toMediaType())

    val widthBody = info?.width?.toString()?.toRequestBody("text/plain".toMediaType())
    val heightBody = info?.height?.toString()?.toRequestBody("text/plain".toMediaType())
    val lengthBody = info?.length?.toString()?.toRequestBody("text/plain".toMediaType())

    val userLoginBody =
        userLogin.toRequestBody("text/plain".toMediaType())

    val response =
        ApiClient.modelApi.uploadFile(
            previewPart,
            filePart,
            nameBody,
            descBody,
            widthBody,
            heightBody,
            lengthBody,
            userLoginBody
        )

    if (response.isSuccessful) {

        return response.body()
    }

    return null
}

private fun isValidGlbFile(
    context: Context,
    uri: Uri
): Pair<Boolean, String?> {

    val resolver = context.contentResolver

    val sizeBytes =
        resolver.openAssetFileDescriptor(uri, "r")
            ?.length
            ?: return false to "Не удалось определить размер файла"

    val maxSizeBytes = 50L * 1024L * 1024L

    if (sizeBytes > maxSizeBytes) {
        return false to "Размер файла превышает 50 МБ"
    }

    val fileName =
        getFileName(context, uri)?.lowercase()

    if (fileName == null || !fileName.endsWith(".glb")) {
        return false to "Поддерживаются только файлы .glb"
    }

    return true to null
}

private fun getFileName(
    context: Context,
    uri: Uri
): String? {

    context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )?.use { cursor ->

        val index =
            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

        if (index >= 0 && cursor.moveToFirst()) {
            return cursor.getString(index)
        }
    }

    return null
}
