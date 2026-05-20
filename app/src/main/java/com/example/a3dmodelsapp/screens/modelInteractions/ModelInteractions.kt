package com.example.a3dmodelsapp.screens.modelInteractions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a3dmodelsapp.screens.catalogue.CatalogueScreen
import com.example.a3dmodelsapp.screens.modelInteractions.info.InfoScreen
import com.example.a3dmodelsapp.screens.modelInteractions.update.UpdateScreen
import com.example.a3dmodelsapp.screens.modelInteractions.viewer.ViewerScreen
import com.example.a3dmodelsapp.screens.upload.UploadScreen
import com.example.a3dmodelsapp.screens.userInfo.UserInfoScreen
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme

@Composable
fun ModelInteractionsNavigation(rootNavController: NavController) {

    val MINavController = rememberNavController()

    NavHost(
        navController = MINavController,
        startDestination = "info",
    ) {
        composable("info") {
            _3DModelsAppTheme{
                InfoScreen(rootNavController, MINavController)
            }
        }
        composable("update") {
            _3DModelsAppTheme{
                UpdateScreen(MINavController)
            }
        }

        composable("viewer") {
            _3DModelsAppTheme{
                ViewerScreen(MINavController)
            }
        }
    }
}