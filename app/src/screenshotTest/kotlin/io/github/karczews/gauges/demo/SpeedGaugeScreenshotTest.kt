package io.github.karczews.gauges.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import io.github.karczews.gauges.speed.SpeedGauge
import io.github.karczews.gauges.speed.SpeedGaugeDefaults

@PreviewTest
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpeedGaugeAtRestPreview() {
    SpeedGauge(speedKmh = 0f)
}

@PreviewTest
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpeedGaugeCruisingPreview() {
    SpeedGauge(speedKmh = 90f)
}

@PreviewTest
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpeedGaugeMaxPreview() {
    SpeedGauge(speedKmh = SpeedGaugeDefaults.MAX_SPEED_KMH)
}
