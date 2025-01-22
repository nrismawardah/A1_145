package com.example.finalucp_145.ui.view.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalucp_145.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TopAppBar(
    onBack: () -> Unit,
    onMainMenuClick: () -> Unit,
    onMainMenuConfirm: () -> Unit,
    judul: String,
    modifier: Modifier = Modifier
) {

    var showConfirmDialog by remember { mutableStateOf(false) }

    val currentDate = remember { LocalDate.now() }
    val formatter = DateTimeFormatter.ofPattern("MMM, yyyy")
    val formattedMonthYear = currentDate.format(formatter)

    val dates = remember {
        (currentDate.minusDays(2)..currentDate.plusDays(5)).toList()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color(0xfffdc938), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Kembali",
                        tint = Color.Black
                    )
                }
            }

            Text(
                text = judul,
                fontSize = 22.sp,
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            IconButton(onClick = { showConfirmDialog = true }) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Main Menu",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formattedMonthYear,
                fontSize = 18.sp,
                color = Color.Black
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                dates.forEach { date ->
                    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    val day = date.dayOfMonth

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = dayOfWeek,
                            fontSize = 14.sp,
                            color = if (date == currentDate) Color(0xfffd8b18) else Color.Black
                        )
                        Text(
                            text = day.toString(),
                            fontSize = 16.sp,
                            color = if (date == currentDate) Color(0xfffd8b18) else Color.Black
                        )
                    }
                }
            }
        }
    }
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    text = "Konfirmasi",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Apakah Anda ingin kembali ke menu utama?",
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
                            showConfirmDialog = false
                            onMainMenuConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3aaffe))
                    ) {
                        Text("Ya", color = Color.White)
                    }
                    Button(
                        onClick = { showConfirmDialog = false },
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

operator fun LocalDate.rangeTo(other: LocalDate): List<LocalDate> {
    return generateSequence(this) { if (it < other) it.plusDays(1) else null }.toList()
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBar() {
    TopAppBar(
        onBack = { },
        onMainMenuClick = { },
        onMainMenuConfirm = { },
        judul = "Daftar Proyek"
    )
}
