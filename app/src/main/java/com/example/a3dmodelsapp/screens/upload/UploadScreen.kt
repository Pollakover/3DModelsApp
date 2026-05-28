package com.example.a3dmodelsapp.screens.upload

import android.content.Context
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.screens.modelInteractions.update.DropdownMenu
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import kotlinx.coroutines.launch
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.a3dmodelsapp.database.ApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun UploadScreen(userLogin: String) {

    val selectedCategories = remember {
        mutableStateListOf<String>()
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var uploadedUrl by remember {
        mutableStateOf("")
    }

    var isUploading by remember {
        mutableStateOf(false)
    }

    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Категория 1") }

    // File Picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            selectedUri = uri

            Toast.makeText(
                context,
                "Файл выбран",
                Toast.LENGTH_SHORT
            ).show()
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

//        AsyncImage(
//            model = "http://192.168.1.6:8080/files/rds.png",
//            contentDescription = "image",
//            modifier = Modifier
//                .size(500.dp)
//                .clip(RoundedCornerShape(20.dp)),
//            contentScale = ContentScale.Crop,
//            placeholder = painterResource(R.drawable.icon),
//            error = painterResource(R.drawable.icon)
//        )

        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {

                    launcher.launch("*/*")
                    // launcher.launch("model/gltf-binary")
                }
                .fillMaxWidth()
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
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(15.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(
                painter = painterResource(R.drawable.upload_24px),
                contentDescription = "",
                tint = primary,
                modifier = Modifier.size(50.dp)
            )

            Text(
                text =
                    if (selectedUri == null)
                        "Выберите файл для загрузки"
                    else
                        "Файл выбран",

                style = CustomTextStyles.body1_semi_bold,
                fontFamily = fontFamily,
                color = textColor
            )


            Text(
                text =
                    if (selectedUri == null)
                        "Формат файла должен быть .glb "
                    else
                        selectedUri.toString(),

                style = CustomTextStyles.body2_regular,
                fontFamily = fontFamily,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
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
                value = name,
                onValueChange = { newText ->
                    if (newText.length <= 200) {
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
                            .clickable(onClick = {
                                selectedCategories.remove(category)
                            },)
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
                if (selectedUri == null) {
                    Toast.makeText(
                        context,
                        "Файл не выбран",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                else {
                    if (name.isEmpty() || desc.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Заполните все поля",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                }
                scope.launch {

                    isUploading = true

                    try {

                        val result = uploadModel(
                            context = context,
                            uri = selectedUri!!,
                            name = name,
                            description = desc,
                            userLogin = userLogin
                        )

                        uploadedUrl = result ?: ""

                        Toast.makeText(
                            context,
                            "Файл загружен",
                            Toast.LENGTH_SHORT
                        ).show()

                    } catch (e: Exception) {

                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    isUploading = false
                }
            },

            shape = CircleShape,

            modifier = Modifier.fillMaxWidth()

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

    name: String,
    description: String,
    userLogin: String,

): String? {

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

    val nameBody =
        name.toRequestBody("text/plain".toMediaType())

    val descBody =
        description.toRequestBody("text/plain".toMediaType())

    val userLoginBody =
        userLogin.toRequestBody("text/plain".toMediaType())

    val response =
        ApiClient.fileApi.uploadFile(
            filePart,
            nameBody,
            descBody,
            userLoginBody
        )

    if (response.isSuccessful) {

        return response.body()?.fileUrl
    }

    return null
}

