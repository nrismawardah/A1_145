package com.example.finalucp_145.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalucp_145.R
import com.example.finalucp_145.ui.navigasi.DestinasiNavigasi

object DestinasiSplash : DestinasiNavigasi {
    override val route = "splash"
    override val titleRes = "Splash"
}

@Composable
fun SplashView(
    onProyekClick: () -> Unit,
    onTimClick: () -> Unit,
    onAnggotaClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logobg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth().size(300.dp)
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = onProyekClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(45.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_assignment),
                    contentDescription = "Proyek Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color.White)
                )
                Text(
                    text = "Manage Proyek",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 50.dp)
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Button(
            onClick = onTimClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(45.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_group),
                    contentDescription = "Tim Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color.White)
                )
                Text(
                    text = "Manage Tim",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 55.dp)
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Button(
            onClick = onAnggotaClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(45.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Anggota Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color.White)
                )
                Text(
                    text = "Manage Anggota",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 45.dp)
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashViewPreview() {
    SplashView(
        onProyekClick = { /* Handle Proyek Click */ },
        onTimClick = { /* Handle Tim Click */ },
        onAnggotaClick = { /* Handle Anggota Click */ }
    )
}

