package com.example.a3dmodelsapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.example.a3dmodelsapp.screens.info.InfoScreen
import com.example.a3dmodelsapp.screens.update.UpdateScreen
import com.example.a3dmodelsapp.screens.viewer.ViewerScreen
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme

class NewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _3DModelsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        //UpdateScreen()
                        //ViewerScreen()
                        //InfoScreen()
                        //NewCheckbox()
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewCheckbox() {
    var isChecked : MutableState<Boolean> = remember {mutableStateOf(true)}
    Checkbox(
        checked = isChecked.value,
        onCheckedChange = {
            Log.i(TAG, "NewCheckbox: $it")
            isChecked.value = it
                          },
        modifier = Modifier.graphicsLayer(scaleX = 4f, scaleY = 4f)
    )
}