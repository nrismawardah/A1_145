package com.example.finalucp_145.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalucp_145.ui.view.DestinasiSplash
import com.example.finalucp_145.ui.view.SplashView
import com.example.finalucp_145.ui.view.proyek.DestinasiHomePry
import com.example.finalucp_145.ui.view.proyek.DestinasiInsertPry
import com.example.finalucp_145.ui.view.proyek.HomePryView
import com.example.finalucp_145.ui.view.proyek.InsertPryView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = Modifier
    ){
        composable(
            route = DestinasiSplash.route
        ){
            SplashView(
                onProyekClick = {
                    navController.navigate(DestinasiHomePry.route)
                },
                onTimClick = {},
                onAnggotaClick = {}
            )
        }
        composable(
            route = DestinasiHomePry.route
        ){
            HomePryView(
                navigateToItemEntry = {navController.navigate(DestinasiInsertPry.route)},
                onDetailClick = {},
                onBack = {navController.popBackStack()},
                navigateToMainMenu = {navController.navigate(DestinasiSplash.route)}
            )
        }
        composable(
            route = DestinasiInsertPry.route
        ){
            InsertPryView(
                onBack = {navController.popBackStack()},
                navigateToMainMenu = {navController.navigate(DestinasiSplash.route)}
            )
        }
    }
}