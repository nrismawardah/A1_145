package com.example.finalucp_145.ui.view.anggota

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.finalucp_145.model.Anggota
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.anggota.DetailAngUiState
import com.example.finalucp_145.ui.viewmodel.anggota.DetailAngViewModel

object DestinasiDetailAng : DestinasiNavigasi {
    override val route = "detail ang"
    override val titleRes = "Detail Ang"
    const val id_anggota = "id_anggota"
    val routeWithArgs = "$route/{$id_anggota}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAngView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    navigateToMainMenu: () -> Unit,
    onEditClick: (String) -> Unit,
    detailAngViewModel: DetailAngViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        detailAngViewModel.getAngById()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .background(Color(0xffE9DCF7))
            ) {
                TopAppBar(
                    judul = "Detail Anggota",
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
                    val id_anggota = (detailAngViewModel.detailAngUiState as? DetailAngUiState.Success)?.anggota?.id_anggota
                    if (id_anggota != null) onEditClick(id_anggota)
                },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xfffdc938)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Anggota",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            DetailStatus(
                angUiState = detailAngViewModel.detailAngUiState,
                retryAction = { detailAngViewModel.getAngById() },
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
    angUiState: DetailAngUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (angUiState) {
        is DetailAngUiState.Success -> {
            DetailAngCard(
                anggota = angUiState.anggota,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailAngUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailAngUiState.Error -> {
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
fun DetailAngCard(
    anggota: Anggota,
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
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ComponentDetailAng(judul = "ID Anggota", isinya = anggota.id_anggota)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailAng(judul = "ID Tim", isinya = anggota.id_tim)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailAng(judul = "Nama Anggota", isinya = anggota.nama_anggota)
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailAng(judul = "Peran Anggota", isinya = anggota.peran)
            }
        }
    }
}

@Composable
fun ComponentDetailAng(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
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
