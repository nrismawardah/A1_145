package com.example.finalucp_145.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalucp_145.ui.view.DestinasiSplash
import com.example.finalucp_145.ui.view.SplashView
import com.example.finalucp_145.ui.view.proyek.DestinasiDetailPry
import com.example.finalucp_145.ui.view.proyek.DestinasiEditPry
import com.example.finalucp_145.ui.view.proyek.DestinasiHomePry
import com.example.finalucp_145.ui.view.proyek.DestinasiInsertPry
import com.example.finalucp_145.ui.view.proyek.DetailPryView
import com.example.finalucp_145.ui.view.proyek.EditPryView
import com.example.finalucp_145.ui.view.proyek.HomePryView
import com.example.finalucp_145.ui.view.proyek.InsertPryView
import com.example.finalucp_145.ui.view.tugas.DestinasiHomeTgs
import com.example.finalucp_145.ui.view.tugas.HomeTgsView

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
                onDetailClick = { id_proyek ->
                    navController.navigate("${DestinasiDetailPry.route}/$id_proyek")
                    println(id_proyek)
                },
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
        composable(
            route = DestinasiDetailPry.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPry.id_proyek){
                type = NavType.StringType
            })
        ){
            backStackEntry ->
            val id_proyek = backStackEntry.arguments?.getString(DestinasiDetailPry.id_proyek)
            id_proyek?.let {
                DetailPryView(
                    onBack = {navController.popBackStack()},
                    onEditClick = { id_proyek ->
                        navController.navigate("${DestinasiEditPry.route}/$id_proyek")
                        println(id_proyek)
                    },
                    navigateToMainMenu = {navController.navigate(DestinasiSplash.route)},
                    navigateToTugas = { proyekId ->
                        navController.navigate("${DestinasiHomeTgs.route}/$proyekId")
                    }
                )
            }
        }
        composable(
            route = DestinasiEditPry.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditPry.id_proyek){
                type = NavType.StringType
            })
        ){
            val id_proyek = it.arguments?.getString(DestinasiEditPry.id_proyek)
            id_proyek?.let {
                EditPryView(
                    onBack = {navController.popBackStack()},
                    navigateToMainMenu = {navController.navigate(DestinasiSplash.route)}
                )
            }
        }

        composable(
            route = DestinasiHomeTgs.routeWithArgs,
            arguments = listOf(navArgument(DestinasiHomeTgs.idProyek) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idProyek = backStackEntry.arguments?.getString(DestinasiHomeTgs.idProyek) ?: return@composable
            HomeTgsView(
                navigateToItemEntryTgs = { },
                navigateToMainMenu = {navController.navigate(DestinasiSplash.route)},
                onBack = {navController.popBackStack()}
            )
        }
    }
}