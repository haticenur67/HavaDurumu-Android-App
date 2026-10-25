package com.example.myapplication.ui.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.City
import com.example.myapplication.ui.LoadState

@Composable
fun CityScreen(
    viewModel: CityViewModel = viewModel(),
    onCityClick: (City) -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()


    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .systemBarsPadding()
    ) {

        Text(
            text = "Hava Durumu ⛅",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp)
        )


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onQueryChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Şehir Ara...") },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.95f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF2193b0)
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onQueryChanged("") }) {
                        Text("X", fontWeight = FontWeight.Bold, color = Color.Gray)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        when (val state = uiState) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            is LoadState.Success -> {
                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    items(state.data) { city ->
                        CityCard(city = city, onClick = { onCityClick(it) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            is LoadState.Empty -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Arama kutusu boşsa dünya, yazılıp sonuç yoksa merteç emojisi gösterir
                        Text(if (searchQuery.isEmpty()) "🌍" else "🔍", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (searchQuery.isEmpty()) "Hava durumunu öğrenmek\niçin bir şehir arayın." else "Sonuç bulunamadı.",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Hata: ${state.message}", color = Color.White, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}