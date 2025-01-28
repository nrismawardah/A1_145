package com.example.finalucp_145.ui.view.proyek


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.proyek.DetailPryUiState
import com.example.finalucp_145.ui.viewmodel.proyek.DetailPryViewModel

object DestinasiDetailPry : DestinasiNavigasi {
    override val route = "detail_pry"
    override val titleRes = "Detail Pry"
    const val id_proyek = "id_proyek"
    val routeWithArgs = "$route/{$id_proyek}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPryView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onEditClick: (String) -> Unit,
    navigateToMainMenu: () -> Unit,
    navigateToTugas: (String) -> Unit,
    detailPryViewModel: DetailPryViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        detailPryViewModel.getProyekById()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .background(Color(0xffE9DCF7))
            ){
                TopAppBar(
                    judul = "Detail Proyek",
                    onBack = onBack,
                    onMainMenuClick = navigateToMainMenu,
                    onMainMenuConfirm = { navigateToMainMenu() },
                    modifier = modifier
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_proyek = (detailPryViewModel.detailPryUiState as? DetailPryUiState.Success)?.proyek?.id_proyek
                    if (id_proyek != null) onEditClick(id_proyek)
                },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xfffdc938)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Proyek",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            DetailPryStatus(
                pryUiState = detailPryViewModel.detailPryUiState,
                retryAction = { detailPryViewModel.getProyekById() },
                navigateToTugas = navigateToTugas,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailPryStatus(
    pryUiState: DetailPryUiState,
    retryAction: () -> Unit,
    navigateToTugas: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (pryUiState) {
        is DetailPryUiState.Success -> {
            Column {
                DetailPryCard(
                    proyek = pryUiState.proyek,
                    modifier = modifier.padding(16.dp)
                )
                Button(
                    onClick = { navigateToTugas(pryUiState.proyek.id_proyek) }, // Navigasi ke daftar tugas
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFAB876),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Tampilkan Tugas")
                }
            }
        }

        is DetailPryUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailPryUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailPryCard(
    proyek: Proyek,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFA3D3FF))
        ){
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ComponentDetailPry(judul = "ID Proyek", isinya = proyek.id_proyek)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailPry(judul = "Nama Proyek", isinya = proyek.nama_proyek)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailPry(judul = "Deskripsi Proyek", isinya = proyek.deskripsi_proyek)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailPry(judul = "Tanggal Mulai", isinya = proyek.tanggal_mulai)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailPry(judul = "Tanggal Berakhir", isinya = proyek.tanggal_berakhir)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailPry(judul = "Status Proyek", isinya = proyek.status_proyek)
            }
        }
    }
}

@Composable
fun ComponentDetailPry(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "$judul:",
            fontSize = 15.sp,
            color = Color.DarkGray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}