package com.example.finalucp_145.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalucp_145.ui.PenyediaViewModel
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
import com.example.finalucp_145.ui.view.tim.DestinasiDetailTim
import com.example.finalucp_145.ui.view.tim.DestinasiEditTim
import com.example.finalucp_145.ui.view.tim.DestinasiHomeTim
import com.example.finalucp_145.ui.view.tim.DestinasiInsertTim
import com.example.finalucp_145.ui.view.tim.DetailTimView
import com.example.finalucp_145.ui.view.tim.EditTimView
import com.example.finalucp_145.ui.view.tim.HomeTimView
import com.example.finalucp_145.ui.view.tim.InsertTimView
import com.example.finalucp_145.ui.view.tugas.DestinasiDetailTgs
import com.example.finalucp_145.ui.view.tugas.DestinasiEditTgs
import com.example.finalucp_145.ui.view.tugas.DestinasiHomeTgs
import com.example.finalucp_145.ui.view.tugas.DestinasiInsertTgs
import com.example.finalucp_145.ui.view.tugas.DetailTgsView
import com.example.finalucp_145.ui.view.tugas.EditTgsView
import com.example.finalucp_145.ui.view.tugas.HomeTgsView
import com.example.finalucp_145.ui.view.tugas.InsertTgsView
import com.example.finalucp_145.ui.viewmodel.tugas.InsertTgsUiEvent
import com.example.finalucp_145.ui.viewmodel.tugas.InsertTgsViewModel

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = Modifier
    ) {
        composable(
            route = DestinasiSplash.route
        ) {
            SplashView(
                onProyekClick = {
                    navController.navigate(DestinasiHomePry.route)
                },
                onTimClick = {
                    navController.navigate(DestinasiHomeTim.route)
                },
                onAnggotaClick = {}
            )
        }
        composable(
            route = DestinasiHomePry.route
        ) {
            HomePryView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPry.route) },
                onDetailClick = { id_proyek ->
                    navController.navigate("${DestinasiDetailPry.route}/$id_proyek")
                    println(id_proyek)
                },
                onBack = { navController.popBackStack() },
                navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
            )
        }
        composable(
            route = DestinasiInsertPry.route
        ) {
            InsertPryView(
                onBack = { navController.popBackStack() },
                navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
            )
        }
        composable(
            route = DestinasiDetailPry.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPry.id_proyek) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id_proyek = backStackEntry.arguments?.getString(DestinasiDetailPry.id_proyek)
            id_proyek?.let {
                DetailPryView(
                    onBack = { navController.popBackStack() },
                    onEditClick = { id_proyek ->
                        navController.navigate("${DestinasiEditPry.route}/$id_proyek")
                        println(id_proyek)
                    },
                    navigateToMainMenu = { navController.navigate(DestinasiSplash.route) },
                    navigateToTugas = { proyekId ->
                        navController.navigate("${DestinasiHomeTgs.route}/$proyekId")
                    }
                )
            }
        }
        composable(
            route = DestinasiEditPry.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditPry.id_proyek) {
                type = NavType.StringType
            })
        ) {
            val id_proyek = it.arguments?.getString(DestinasiEditPry.id_proyek)
            id_proyek?.let {
                EditPryView(
                    onBack = { navController.popBackStack() },
                    navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
                )
            }
        }

        composable(
            route = DestinasiHomeTgs.routeWithArgs,
            arguments = listOf(navArgument(DestinasiHomeTgs.idProyek) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idProyek =
                backStackEntry.arguments?.getString(DestinasiHomeTgs.idProyek) ?: return@composable
            HomeTgsView(
                navigateToItemEntryTgs = {
                    navController.navigate("${DestinasiInsertTgs.route}/$idProyek")
                },
                navigateToMainMenu = { navController.navigate(DestinasiSplash.route) },
                onBack = { navController.popBackStack() },
                onDetailClick = { id_tugas ->
                    navController.navigate("${DestinasiDetailTgs.route}/$id_tugas")
                    println(id_tugas)
                },
            )
        }
        composable(
            route = "${DestinasiInsertTgs.route}/{idProyek}",
            arguments = listOf(navArgument("idProyek") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idProyek = backStackEntry.arguments?.getString("idProyek") ?: return@composable
            val viewModel: InsertTgsViewModel = viewModel(factory = PenyediaViewModel.Factory)
            InsertTgsView(
                onBack = { navController.popBackStack() },
                navigateToMainMenu = { navController.navigate(DestinasiSplash.route) },
                viewModel = viewModel.apply {
                    updateInsertTgsState(
                        InsertTgsUiEvent(idProyek = idProyek)
                    )
                }
            )
        }
        composable(
            route = DestinasiDetailTgs.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailTgs.id_tugas) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id_tugas = backStackEntry.arguments?.getString(DestinasiDetailTgs.id_tugas)
            id_tugas?.let {
                DetailTgsView(
                    onBack = { navController.popBackStack() },
                    onEditClick = { id_tugas ->
                        navController.navigate("${DestinasiEditTgs.route}/$id_tugas")
                        println(id_tugas)
                    },
                    navigateToMainMenu = { navController.navigate(DestinasiSplash.route) },
                )
            }
        }
        composable(
            route = DestinasiEditTgs.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditTgs.id_tugas) {
                type = NavType.StringType
            })
        ) {
            val id_tugas = it.arguments?.getString(DestinasiEditTgs.id_tugas)
            id_tugas?.let {
                EditTgsView(
                    onBack = { navController.popBackStack() },
                    navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
                )
            }
        }

        composable(
            route = DestinasiHomeTim.route
        ) {
            HomeTimView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertTim.route) },
                onDetailClick = {  id_tim ->
                    navController.navigate("${DestinasiDetailTim.route}/$id_tim")
                    println(id_tim) },
                onBack = { navController.popBackStack() },
                navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
            )
        }
        composable(
            route = DestinasiInsertTim.route
        ) {
            InsertTimView(
                onBack = { navController.popBackStack() },
                navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
            )
        }
        composable(
            route = DestinasiDetailTim.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailTim.id_tim) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id_tim = backStackEntry.arguments?.getString(DestinasiDetailTim.id_tim)
            id_tim?.let {
                DetailTimView(
                    onBack = { navController.popBackStack() },
                    onEditClick = { id_tim ->
                        navController.navigate("${DestinasiEditTim.route}/$id_tim")
                        println(id_tim)
                    },
                    navigateToMainMenu = { navController.navigate(DestinasiSplash.route) },

                    )
            }
        }
        composable(
            route = DestinasiEditTim.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditTim.id_tim) {
                type = NavType.StringType
            })

        ) {
            val id_tim = it.arguments?.getString(DestinasiEditTim.id_tim)
            id_tim?.let {
                EditTimView(
                    onBack = { navController.popBackStack() },
                    navigateToMainMenu = { navController.navigate(DestinasiSplash.route) }
                )
            }
        }
    }
}

