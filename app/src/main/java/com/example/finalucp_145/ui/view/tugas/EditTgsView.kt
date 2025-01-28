package com.example.finalucp_145.ui.view.tugas

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.tim.HomeTimViewModel
import com.example.finalucp_145.ui.viewmodel.tugas.EditTgsViewModel
import kotlinx.coroutines.launch

object DestinasiEditTgs : DestinasiNavigasi {
    override val route = "edit_tgs"
    override val titleRes = "Edit Tgs"
    const val id_tugas = "id_tugas"
    val routeWithArgs = "$route/{$id_tugas}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTgsView(
    onBack: () -> Unit,
    navigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditTgsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    timViewModel : HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val timUistate = timViewModel.timUiState
    val selectedTim by remember { mutableStateOf(timViewModel.selectedTim) }

    Scaffold (
        modifier = modifier.fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .background(Color(0xffE9DCF7))
            ){
                TopAppBar(
                    judul = "Edit Tugas",
                    onBack = onBack,
                    onMainMenuClick = navigateToMainMenu,
                    onMainMenuConfirm = { navigateToMainMenu() },
                    modifier = modifier
                )
            }
        }
    ){ innerPadding ->
        InsertTgsBody(
            insertTgsUiState = viewModel.editUiState,
            onTugasValueChange = viewModel::updateInsertTgsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editTugas()
                    onBack()
                }
            },
            timUistate = timUistate,
            selectedTim = selectedTim,
            onTimSelected = { timViewModel.selectTim(it) },
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        )
    }
}