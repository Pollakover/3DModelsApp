package com.example.a3dmodelsapp.screens.modelInteractions

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a3dmodelsapp.database.models.Model
import com.example.a3dmodelsapp.screens.modelInteractions.info.InfoScreen
import com.example.a3dmodelsapp.screens.modelInteractions.update.UpdateScreen
import com.example.a3dmodelsapp.screens.modelInteractions.viewer.ViewerScreen
import com.example.a3dmodelsapp.viewModels.MainViewModel

@Composable
fun ModelInteractionsNavigation(
    rootNavController: NavController,
    model: Model?,
    viewModel: MainViewModel
) {
    val MINavController = rememberNavController()

    NavHost(
        navController = MINavController,
        startDestination = "info",
    ) {
        composable("info") {
            InfoScreen(rootNavController, MINavController, viewModel)
        }
        composable("update") {
            UpdateScreen(
                MINavController = MINavController,
                rootNavController = rootNavController,  // Передаем корневой навигатор
                viewModel = viewModel
            )
        }
        composable("viewer") {
            ViewerScreen(MINavController, model)
        }
    }
}