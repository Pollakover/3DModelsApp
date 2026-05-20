package com.example.a3dmodelsapp.screens.upload

import android.R.attr.level
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
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
import com.example.a3dmodelsapp.database.SupabaseClient
import com.example.a3dmodelsapp.screens.modelInteractions.update.DropdownMenu
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.uploadAsFlow
import java.io.ByteArrayOutputStream
import java.io.File
import io.github.jan.supabase.storage.UploadData
import io.ktor.utils.io.jvm.javaio.toByteReadChannel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.util.concurrent.TimeUnit

@Composable
fun UploadScreen() {

    val url = SupabaseClient.client.storage.from("3d-models").publicUrl("BOX.glb")
    Log.d("TEST", url)

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

        // SELECT FILE
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {

                    launcher.launch("*/*")
                    // Можно:
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
                        ".glb / .gltf"
                    else
                        selectedUri.toString(),

                style = CustomTextStyles.body2_regular,
                fontFamily = fontFamily,
                color = textColor
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Описание",
                fontFamily = fontFamily,
                ////color = MaterialTheme.colorScheme.onBackground,
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
                    //.clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        color = borderColor,
                        //CircleShape,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(14.dp, 10.dp, 14.dp, 10.dp),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontFamily
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        //contentAlignment = Alignment.CenterStart
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
                selectedField = status,
                onFieldSelected = { newStatus ->
                    status = newStatus
                },
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(21) {index ->
                        Badge(
                            containerColor = secondary,
                            contentColor = textColor
                        ) {
                            Text(
                                "Категория $index",
                                modifier = Modifier.padding(5.dp),
                                style = CustomTextStyles.body2_regular,
                                color = textColor,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }
            )
        }

        Button(
            onClick = {

                if (selectedUri == null) {

                    Toast.makeText(
                        context,
                        "Выберите файл",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                scope.launch(Dispatchers.IO) {
                    try {

                        isUploading = true

                        val bytes = context.contentResolver
                            .openInputStream(selectedUri!!)
                            ?.readBytes()

                        if (bytes != null) {

                            val fileName = "${System.currentTimeMillis()}.png"

                            Log.d("FILE_SIZE", "${bytes.size / 1024} KB")
                            Log.d("Bytes", bytes.toString())

//                            val url = uploadFile(
//                                fileName = fileName,
//                                uri = selectedUri!!,
//                                context = context
//                            )

                            uploadFileOkHttp(context, selectedUri!!, fileName)

                            uploadedUrl = url

                            Log.d("SUPABASE", url)

//                            Toast.makeText(
//                                context,
//                                "Файл загружен",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    } catch (e: Exception) {
                        Log.e("UPLOAD_ERROR", e.message, e)
                        e.printStackTrace()

//                        Toast.makeText(
//                            context,
//                            "Ошибка загрузки",
//                            Toast.LENGTH_LONG
//                        ).show()

                    } finally {

                        isUploading = false
                    }
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

        // URL
        if (uploadedUrl.isNotEmpty()) {

            Text(
                text = uploadedUrl,
                color = primary
            )
        }
    }
}

suspend fun uploadFile(
    context: Context,
    uri: Uri,
    fileName: String
): String {

    val bucket = SupabaseClient.client.storage.from("3d-models")

    val file = uriToFile(context, uri, fileName)

    val uploadData = UploadData(
        stream = file.inputStream().toByteReadChannel(),
        size = file.length()
    )

    bucket.uploadAsFlow(
        path = fileName,
        data = uploadData
    ).collect { status ->

        when (status) {

            is UploadStatus.Progress -> {
                val percent =
                    status.totalBytesSend.toFloat() /
                            status.contentLength.toFloat() * 100f

                Log.d("UPLOAD", "Progress: $percent%")
            }

            is UploadStatus.Success -> {
                Log.d("UPLOAD", "SUCCESS")
            }
        }
    }

    return bucket.publicUrl(fileName)
}


fun uriToFile(context: Context, uri: Uri, fileName: String): File {
    val file = File(context.cacheDir, fileName)

    context.contentResolver.openInputStream(uri)?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file
}

val client = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.MINUTES)
    .readTimeout(60, TimeUnit.SECONDS)
    .protocols(listOf(Protocol.HTTP_1_1))
    .build()

fun uploadFileOkHttp(
    context: Context,
    uri: Uri,
    fileName: String
) {

    val file = uriToFile(context, uri, fileName)

    val requestBody = file.asRequestBody("image/png".toMediaType())

    val request = Request.Builder()
        .url("https://piggzxpuluznhahqkccp.supabase.co/storage/v1/object/3d-models/$fileName")
        .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBpZ2d6eHB1bHV6bmhhaHFrY2NwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzkwOTExMzYsImV4cCI6MjA5NDY2NzEzNn0.fMRGRhhOr_0wBI-62STgGD5-jczRPrJNh8xa4dYQ6oM")
        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBpZ2d6eHB1bHV6bmhhaHFrY2NwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzkwOTExMzYsImV4cCI6MjA5NDY2NzEzNn0.fMRGRhhOr_0wBI-62STgGD5-jczRPrJNh8xa4dYQ6oM")
        .put(requestBody)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw Exception("Upload failed: ${response.code}")
        }
    }
}