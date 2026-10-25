package com.example.myapplication.ui.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.WeatherCondition
import com.example.myapplication.ui.LoadState

@Composable
fun ForecastScreen(
    lat: Double,
    lon: Double,
    cityName: String,
    viewModel: ForecastViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.fetchForecast(lat, lon)
    }

    val uiState by viewModel.uiState.collectAsState()

    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(androidx.compose.foundation.rememberScrollState())
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Text("←", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        when (val state = uiState) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.message, color = Color.White, fontSize = 18.sp)
                }
            }
            is LoadState.Success -> {
                val current = state.data.current
                val condition = WeatherCondition.get(current.weatherCode)


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = condition.first, fontSize = 100.sp)
                    Text(
                        text = "${current.temperature}°C",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = condition.second,
                        fontSize = 24.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Spacer(modifier = Modifier.height(16.dp))

                HourlyChart(hourly = state.data.hourly)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "7 Günlük Tahmin",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        for (index in state.data.daily.time.indices) {
                            val dailyCondition = WeatherCondition.get(state.data.daily.weatherCode[index])
                            val max = state.data.daily.temperatureMax[index]
                            val min = state.data.daily.temperatureMin[index]
                            val date = state.data.daily.time[index]

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = date, color = Color.White, fontSize = 16.sp)
                                Text(text = dailyCondition.first, fontSize = 24.sp)
                                Text(
                                    text = "$min° / $max°",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            if (index < state.data.daily.time.size - 1) {
                                HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

// Özel Tasarım: Jetpack Compose Canvas ve Native Canvas kullanarak
// 24 saatlik sıcaklık değişimini yumuşak eğrilerle (Bezier Curve) çizen fonksiyon.
@Composable
fun HourlyChart(hourly: com.example.myapplication.model.HourlyForecast) {
    val temps = hourly.temperature.take(24).map { it.toFloat() }
    val times = hourly.time.take(24).map { it.substring(11, 16) }

    val maxTemp = temps.maxOrNull() ?: 0f
    val minTemp = temps.minOrNull() ?: 0f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(androidx.compose.foundation.rememberScrollState())
            .padding(vertical = 24.dp)
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .width(1000.dp)
                .height(120.dp)
                .padding(horizontal = 24.dp)
        ) {
            val width = size.width
            val height = size.height
            val spacing = width / (temps.size - 1)
            val yRange = if (maxTemp - minTemp == 0f) 1f else (maxTemp - minTemp)

            val path = androidx.compose.ui.graphics.Path()
            var previousX = 0f
            var previousY = height - ((temps[0] - minTemp) / yRange) * height

            path.moveTo(previousX, previousY)


            for (i in 0 until temps.size) {
                val x = i * spacing
                val y = height - ((temps[i] - minTemp) / yRange) * height

                if (i > 0) {
                    path.cubicTo(
                        x1 = (x + previousX) / 2, y1 = previousY,
                        x2 = (x + previousX) / 2, y2 = y,
                        x3 = x, y3 = y
                    )
                }
                previousX = x
                previousY = y
            }


            drawPath(
                path = path,
                color = androidx.compose.ui.graphics.Color.White,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 4.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )


            val textPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 36f
                textAlign = android.graphics.Paint.Align.CENTER
                isAntiAlias = true
            }


            for (i in 0 until temps.size) {
                val x = i * spacing
                val y = height - ((temps[i] - minTemp) / yRange) * height

                drawCircle(
                    color = androidx.compose.ui.graphics.Color(0xFF6dd5ed),
                    radius = 6.dp.toPx(),
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
                drawCircle(
                    color = androidx.compose.ui.graphics.Color.White,
                    radius = 4.dp.toPx(),
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )


                drawContext.canvas.nativeCanvas.drawText("${temps[i].toInt()}°", x, y - 25f, textPaint)
                drawContext.canvas.nativeCanvas.drawText(times[i], x, height + 50f, textPaint)
            }
        }
    }
}