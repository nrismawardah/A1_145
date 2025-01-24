package com.example.finalucp_145.ui.view.tugas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.R
import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.tugas.HomeTgsUiState
import com.example.finalucp_145.ui.viewmodel.tugas.HomeTgsViewModel

object DestinasiHomeTgs : DestinasiNavigasi {
    override val route = "home_tgs"
    override val titleRes = "Home Tgs"
    const val idProyek = "id_proyek"
    val routeWithArgs = "$route/{$idProyek}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTgsView(
    navigateToItemEntryTgs: () -> Unit,
    navigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onBack: () -> Unit,
    viewModel: HomeTgsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val showDialog = remember { mutableStateOf(false) }
    val tugasToDelete = remember { mutableStateOf<Tugas?>(null) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .background(Color.White)
            ) {
                TopAppBar(
                    judul = "Daftar Tugas",
                    onBack = onBack,
                    onMainMenuClick = navigateToMainMenu,
                    onMainMenuConfirm = { navigateToMainMenu() },
                    modifier = modifier
                )
            }
        },
        containerColor = Color(0xffE9DCF7),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryTgs,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xfffdc938)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Tugas")
            }
        },
    ) { innerPadding ->
        HomeTgsStatus(
            homeTgsUiState = viewModel.tgsUiState,
            retryAction = { viewModel.getTgs() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { tugas ->
                tugasToDelete.value = tugas
                showDialog.value = true
            }
        )
    }
    if (showDialog.value && tugasToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(
                    text = "Konfirmasi Penghapusan",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin menghapus tugas '${tugasToDelete.value?.nama_tugas}'?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            tugasToDelete.value?.let {
                                viewModel.deleteTgs(it.id_tugas)
                                viewModel.getTgs()  // Memuat ulang data setelah penghapusan
                            }
                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3aaffe))
                    ) {
                        Text("Ya", color = Color.White)
                    }
                    Button(
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3aaffe))
                    ) {
                        Text("Tidak", color = Color.White)
                    }
                }
            },
            dismissButton = {}
        )
    }
}

@Composable
fun HomeTgsStatus(
    homeTgsUiState: HomeTgsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tugas) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeTgsUiState) {
        is HomeTgsUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTgsUiState.Success -> {
            if (homeTgsUiState.tugas.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data tugas")
                }
            } else {
                TgsLayout(
                    tugas = homeTgsUiState.tugas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_tugas) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeTgsUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(20.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "",
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TgsLayout(
    tugas: List<Tugas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tugas) -> Unit,
    onDeleteClick: (Tugas) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tugas) { tugas ->
            TgsCard(
                tugas = tugas,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clickable { onDetailClick(tugas) },
                onDeleteClick = {
                    onDeleteClick(tugas)
                }
            )
        }
    }
}

@Composable
fun TgsCard(
    tugas: Tugas,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tugas) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = tugas.nama_tugas,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold

                )
                Divider(
                    color = Color(0xffE9DCF7),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = tugas.prioritas,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val iconRes = when (tugas.status_tugas) {
                    "Belum mulai" -> R.drawable.ic_belummulai
                    "Sedang berlangsung" -> R.drawable.ic_sdgberlangsung
                    "Selesai" -> R.drawable.ic_selesai
                    else -> R.drawable.ic_help
                }
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = tugas.status_tugas,
                    tint = Color(0xff3aaffe),
                    modifier = Modifier.size(30.dp)
                )
                IconButton(onClick = { onDeleteClick(tugas) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xfffe8b18)
                    )
                }
            }
        }
    }
}