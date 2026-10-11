package io.github.karczews.gauges.demo.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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

public object SpeedGaugeDemo : GaugeDemo {
    override val id: String = "speed"
    override val title: String = "SpeedGauge"
    override val description: String = "Analog speedometer, 0–260 km/h."

    @Composable
    override fun Content(modifier: Modifier) {
        var speed by rememberSaveable { mutableFloatStateOf(90f) }
        var outsideTempC by rememberSaveable { mutableStateOf("21°C") }
        var tripValue by rememberSaveable { mutableStateOf("128.4") }
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            // Wide (desktop/web) windows get a side-by-side layout so the
            // square gauge stops growing with the window width. The gauge
            // width is derived from both constraints so shrinking the browser
            // window shrinks the gauge instead of pushing content off-screen.
            if (maxWidth >= 860.dp) {
                val gaugeWidth = minOf(
                    (maxWidth - 80.dp) / 2,
                    maxHeight - 80.dp,
                    620.dp,
                ).coerceAtLeast(200.dp)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        32.dp,
                        Alignment.CenterHorizontally,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SpeedGauge(
                        speedKmh = speed,
                        modifier = Modifier.width(gaugeWidth),
                        outsideTempC = outsideTempC,
                        tripValue = tripValue,
                    )
                    Controls(
                        speed = speed,
                        onSpeedChange = { speed = it },
                        outsideTempC = outsideTempC,
                        onOutsideTempChange = { outsideTempC = it },
                        tripValue = tripValue,
                        onTripValueChange = { tripValue = it },
                        modifier = Modifier.widthIn(max = 420.dp),
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SpeedGauge(
                        speedKmh = speed,
                        modifier = Modifier.fillMaxWidth().widthIn(max = 560.dp),
                        outsideTempC = outsideTempC,
                        tripValue = tripValue,
                    )
                    Controls(
                        speed = speed,
                        onSpeedChange = { speed = it },
                        outsideTempC = outsideTempC,
                        onOutsideTempChange = { outsideTempC = it },
                        tripValue = tripValue,
                        onTripValueChange = { tripValue = it },
                        modifier = Modifier.fillMaxWidth().widthIn(max = 560.dp),
                    )
                }
            }
        }
    }

    @Composable
    private fun Controls(
        speed: Float,
        onSpeedChange: (Float) -> Unit,
        outsideTempC: String,
        onOutsideTempChange: (String) -> Unit,
        tripValue: String,
        onTripValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(modifier = modifier) {
            Text(
                text = "${speed.toInt()} km/h",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
            )
            Slider(
                value = speed,
                onValueChange = onSpeedChange,
                valueRange = 0f..SpeedGaugeDefaults.MAX_SPEED_KMH,
                modifier = Modifier.padding(horizontal = 24.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = outsideTempC,
                    onValueChange = onOutsideTempChange,
                    label = { Text("Outside") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
                OutlinedTextField(
                    value = tripValue,
                    onValueChange = onTripValueChange,
                    label = { Text("Trip") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SpeedGaugeDemoPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        SpeedGaugeDemo.Content(modifier = Modifier)
    }
}
