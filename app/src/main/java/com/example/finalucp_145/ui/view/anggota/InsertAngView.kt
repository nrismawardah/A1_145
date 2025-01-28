package com.example.finalucp_145.ui.view.anggota

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.finalucp_145.ui.view.tugas.DropDownMenu
import com.example.finalucp_145.ui.viewmodel.anggota.InsertAngUiEvent
import com.example.finalucp_145.ui.viewmodel.anggota.InsertAngUiState
import com.example.finalucp_145.ui.viewmodel.anggota.InsertAngViewModel
import com.example.finalucp_145.ui.viewmodel.tim.HomeTimUiState
import com.example.finalucp_145.ui.viewmodel.tim.HomeTimViewModel
import kotlinx.coroutines.launch

object DestinasiInsertAng : DestinasiNavigasi {
    override val route = "insert_ang"
    override val titleRes = "Insert Ang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAngView(
    onBack: () -> Unit,
    navigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAngViewModel = viewModel(factory = PenyediaViewModel.Factory),
    timViewModel : HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val timUistate = timViewModel.timUiState
    val selectedTim by remember { mutableStateOf(timViewModel.selectedTim) }

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
                    judul = "Tambah Anggota",
                    onBack = onBack,
                    onMainMenuClick = navigateToMainMenu,
                    onMainMenuConfirm = { navigateToMainMenu() },
                    modifier = modifier
                )
            }
        },
    ) { innerPadding ->
        InsertAngBody(
            insertAngUiState = viewModel.angUiState,
            onAngValueChange = viewModel::updateInsertAngState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAng()
                    onBack()
                }
            },
            timUistate = timUistate,
            selectedTim = selectedTim,
            onTimSelected = { timViewModel.selectTim(it) },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InsertAngBody(
    insertAngUiState: InsertAngUiState,
    onAngValueChange: (InsertAngUiEvent) -> Unit,
    timUistate: HomeTimUiState,
    selectedTim: String,
    onTimSelected: (String) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        FormInsertAng(
            insertAngUiEvent = insertAngUiState.insertAngUiEvent,
            onValueChange = onAngValueChange,
            modifier = Modifier.fillMaxWidth(),
            timUistate = timUistate,
            selectedTim = selectedTim,
            onTimSelected = onTimSelected

        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
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
fun FormInsertAng(
    insertAngUiEvent: InsertAngUiEvent,
    modifier: Modifier = Modifier,
    timUistate: HomeTimUiState,
    selectedTim: String,
    onTimSelected: (String) -> Unit = {},
    onValueChange: (InsertAngUiEvent) -> Unit = {},
    enabled: Boolean = true
) {

    val peranList = listOf("Ketua", "Anggota")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = insertAngUiEvent.id_anggota,
            onValueChange = { onValueChange(insertAngUiEvent.copy(id_anggota = it)) },
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        when(timUistate) {
            is HomeTimUiState.Loading -> {
                Text(text = "Loading")
            }
            is HomeTimUiState.Error -> {
                Text(text = "Error")
            }
            is HomeTimUiState.Success -> {
                DropDownMenu(
                    title = "ID Tim",
                    options = timUistate.tim.map { it.id_tim},
                    selectedOption = selectedTim,
                    onOptionSelected = { id_tim ->
                        onTimSelected(id_tim)
                        onValueChange(insertAngUiEvent.copy(id_tim = id_tim))
                    }
                )
            }
        }
        OutlinedTextField(
            value = insertAngUiEvent.nama_anggota,
            onValueChange = { onValueChange(insertAngUiEvent.copy(nama_anggota = it)) },
            label = { Text("Nama Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertAngUiEvent.peran,
            onValueChange = { onValueChange(insertAngUiEvent.copy(peran = it)) },
            label = { Text("Peran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Text(
            text = "Peran Anggota"
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            peranList.forEach { peran ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = insertAngUiEvent.peran == peran,
                        onClick = {
                            onValueChange(insertAngUiEvent.copy(peran = peran))
                        },
                    )
                    Text(
                        text = peran,
                    )
                }
            }
        }

        @Composable
        fun DropDownMenu(
            title: String,
            options: List<String>,
            selectedOption: String,
            onOptionSelected: (String) -> Unit,
            isError: Boolean = false,
            errorMessage: String = ""
        ) {
            var expanded by remember { mutableStateOf(false) }
            var currentSelected by remember { mutableStateOf(selectedOption) }

            Column {
                OutlinedTextField(
                    value = currentSelected,
                    onValueChange = { },
                    label = { Text(title) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if(expanded) Icons . Default . ArrowDropDown else Icons . Default . ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onOptionSelected(option)
                                currentSelected = option
                                expanded = false
                            }
                        )
                    }
                }
                if (isError && errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = Color.Red
                    )
                }
            }
        }
    }
}
