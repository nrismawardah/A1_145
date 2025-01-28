package com.example.finalucp_145.ui.view.anggota

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.anggota.EditAngViewModel
import com.example.finalucp_145.ui.viewmodel.tim.HomeTimViewModel
import kotlinx.coroutines.launch

object DestinasiEditAng : DestinasiNavigasi {
    override val route = "edit_ang"
    override val titleRes = "Edit Ang"
    const val id_anggota = "id_anggota"
    val routeWithArgs = "$route/{$id_anggota}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAngView(
    onBack: () -> Unit,
    navigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditAngViewModel = viewModel(factory = PenyediaViewModel.Factory),
    timViewModel: HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val selectedTim = timViewModel.selectedTim
    val timUistate = timViewModel.timUiState

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .background(Color(0xffE9DCF7))
            ) {
                TopAppBar(
                    judul = "Edit Anggota",
                    onBack = onBack,
                    onMainMenuClick = navigateToMainMenu,
                    onMainMenuConfirm = { navigateToMainMenu() },
                    modifier = modifier
                )
            }
        }
    ) { innerPadding ->
        InsertAngBody(
            insertAngUiState = viewModel.uiState,
            onAngValueChange = viewModel::updateInsertAngState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editAng()
                    onBack()
                }
            },
            timUistate = timUistate,
            selectedTim = selectedTim,
            onTimSelected = { timViewModel.selectTim(it) },
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxWidth()
        )
    }
}
