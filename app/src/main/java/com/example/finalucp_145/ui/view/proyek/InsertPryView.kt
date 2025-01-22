package com.example.finalucp_145.ui.view.proyek

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.proyek.InsertPryViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.InsertUiEvent
import com.example.finalucp_145.ui.viewmodel.proyek.InsertUiState
import kotlinx.coroutines.launch

object DestinasiInsertPry : DestinasiNavigasi {
    override val route = "insert_pry"
    override val titleRes = "Insert Pry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPryView(
    onBack: () -> Unit,
    navigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
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
                    judul = "Tambah Proyek",
                    onBack = onBack,
                    onMainMenuClick = navigateToMainMenu,
                    onMainMenuConfirm = { navigateToMainMenu() },
                    modifier = modifier
                )
            }
        },

        ) { innerPadding ->
        InsertPryBody(
            insertUiState = viewModel.uiState,
            onPryValueChange = viewModel::updateInsertPryState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPry()
                    onBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InsertPryBody(
    insertUiState: InsertUiState,
    onPryValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        FormInsertPry(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPryValueChange,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xff3aaffe)
            )
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInsertPry(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val statusProyek = listOf("Belum mulai", "Sedang berlangsung", "Selesai")

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_proyek,
            onValueChange = { onValueChange(insertUiEvent.copy(id_proyek = it)) },
            label = { Text("ID Proyek") },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nama_proyek,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_proyek = it)) },
            label = { Text("Nama Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_proyek,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_proyek = it)) },
            label = { Text("Deskripsi Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Beri jarak antar field
            modifier = Modifier.fillMaxWidth() // Isi seluruh lebar
        ) {
            OutlinedTextField(
                value = insertUiEvent.tanggal_mulai,
                onValueChange = { onValueChange(insertUiEvent.copy(tanggal_mulai = it)) },
                label = { Text("Tanggal Mulai") },
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = insertUiEvent.tanggal_berakhir,
                onValueChange = { onValueChange(insertUiEvent.copy(tanggal_berakhir = it)) },
                label = { Text("Tanggal Berakhir") },
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                singleLine = true
            )
        }
        Spacer(
            modifier = Modifier
        )
        Text(
            text = "Status Proyek"
        )
        Column(
            //verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            statusProyek.forEach { sts ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = insertUiEvent.status_proyek == sts,
                        onClick = {
                            onValueChange(insertUiEvent.copy(status_proyek = sts))
                        },
                    )
                    Text(
                        text = sts,
                    )
                }
            }
        }
    }
}