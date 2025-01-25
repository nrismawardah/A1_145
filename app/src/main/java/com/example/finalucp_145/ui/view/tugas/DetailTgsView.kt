package com.example.finalucp_145.ui.view.tugas

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.tugas.DetailTgsUiState
import com.example.finalucp_145.ui.viewmodel.tugas.DetailTgsViewModel

object DestinasiDetailTgs : DestinasiNavigasi {
    override val route = "detail tgs"
    override val titleRes = "Detail Tgs"
    const val id_tugas = "id_tugas"
    val routeWithArgs = "$route/{$id_tugas}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTgsView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    navigateToMainMenu: () -> Unit,
    onEditClick: (String) -> Unit,
    detailTgsViewModel: DetailTgsViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                    judul = "Detail Tugas",
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
                    val id_tugas = (detailTgsViewModel.detailTgsUiState as? DetailTgsUiState.Success)?.tugas?.id_tugas
                    if (id_tugas != null) onEditClick(id_tugas)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tugas",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailStatus(
                tgsUiState = detailTgsViewModel.detailTgsUiState,
                retryAction = { detailTgsViewModel.getTgsById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailStatus(
    tgsUiState: DetailTgsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (tgsUiState) {
        is DetailTgsUiState.Success -> {
            DetailTgsCard(
                tugas = tgsUiState.tugas,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailTgsUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailTgsUiState.Error -> {
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
fun DetailTgsCard(
    tugas: Tugas,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailTgs(judul = "ID Tugas", isinya = tugas.id_tugas)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTgs(judul = "ID Proyek", isinya = tugas.id_proyek)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTgs(judul = "ID Tim", isinya = tugas.id_tim)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTgs(judul = "Nama Tugas", isinya = tugas.nama_tugas)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTgs(judul = "Deskripsi Tugas", isinya = tugas.deskripsi_tugas)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTgs(judul = "Prioritas Tugas", isinya = tugas.prioritas)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTgs(judul = "Status Tugas", isinya = tugas.status_tugas)
        }
    }
}

@Composable
fun ComponentDetailTgs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$judul:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}