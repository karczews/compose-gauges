package io.github.karczews.gauges.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.karczews.gauges.speed.SpeedGauge
import io.github.karczews.gauges.speed.SpeedGaugeDefaults

@Composable
fun SpeedGaugeScreen(modifier: Modifier = Modifier) {
    var speed by rememberSaveable { mutableFloatStateOf(15f) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SpeedGauge(
            speedKmh = speed,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "${speed.toInt()} km/h",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
        Slider(
            value = speed,
            onValueChange = { speed = it },
            valueRange = 0f..SpeedGaugeDefaults.MAX_SPEED_KMH,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SpeedGaugeScreenPreview() {
    SpeedGaugeScreen()
}
