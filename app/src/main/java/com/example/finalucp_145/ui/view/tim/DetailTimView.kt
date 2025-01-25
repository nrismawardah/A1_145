package com.example.finalucp_145.ui.view.tim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_145.model.Tim
import com.example.finalucp_145.ui.PenyediaViewModel
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi
import com.example.finalucp_145.ui.view.customwidget.TopAppBar
import com.example.finalucp_145.ui.viewmodel.tim.DetailTimUiState
import com.example.finalucp_145.ui.viewmodel.tim.DetailTimViewModel

object DestinasiDetailTim : DestinasiNavigasi {
    override val route = "detail_tim"
    override val titleRes = "Detail Tim"
    const val id_tim = "id_tim"
    val routeWithArgs = "$route/{$id_tim}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTimView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onEditClick: (String) -> Unit,
    navigateToMainMenu: () -> Unit,
    detailTimViewModel: DetailTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                    judul = "Detail Tim",
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
                    val id_tim = (detailTimViewModel.detailTimUiState as? DetailTimUiState.Success)?.tim?.id_tim
                    if (id_tim != null) onEditClick(id_tim)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tim",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            DetailTimStatus(
                timUiState = detailTimViewModel.detailTimUiState,
                retryAction = { detailTimViewModel.getTimById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailTimStatus(
    timUiState: DetailTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (timUiState) {
        is DetailTimUiState.Success -> {
            DetailTimCard(
                tim = timUiState.tim,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailTimUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailTimUiState.Error -> {
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
fun DetailTimCard(
    tim: Tim,
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
            ComponentDetailTim(judul = "ID Tim", isinya = tim.id_tim)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTim(judul = "Nama Tim", isinya = tim.nama_tim)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailTim(judul = "Deskripsi Tim", isinya = tim.deskripsi_tim)
        }
    }
}

@Composable
fun ComponentDetailTim(
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
