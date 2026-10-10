package io.github.karczews.gauges.speed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.karczews.gauges.speed.SpeedGaugeDefaults.MAX_SPEED_KMH
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

internal const val START_ANGLE_DEG = 135f
internal const val SWEEP_DEG = 270f
internal const val GAUGE_MARGIN_FACTOR = 0.94f

internal fun speedToAngle(
    speedKmh: Float,
    startAngleDeg: Float = START_ANGLE_DEG,
    sweepDeg: Float = SWEEP_DEG,
    maxSpeedKmh: Float = MAX_SPEED_KMH,
): Float {
    val clamped = speedKmh.coerceIn(0f, maxSpeedKmh)
    return startAngleDeg + (clamped / maxSpeedKmh) * sweepDeg
}

private fun Float.toRadians(): Float = this * PI.toFloat() / 180f

/**
 * Analog speedometer with a 0–260 km/h dial, an animated needle and a trip computer strip.
 *
 * @param speedKmh current speed; values outside 0..[SpeedGaugeDefaults.MAX_SPEED_KMH] are clamped.
 * @param outsideTempC text shown in the "OUTSIDE" field of the trip computer strip.
 * @param tripLabel trip selector label, e.g. "TRIP B".
 * @param tripValue trip distance text.
 * @param colors colors used to draw the gauge.
 */
@Composable
public fun SpeedGauge(
    speedKmh: Float,
    modifier: Modifier = Modifier,
    outsideTempC: String = "21°C",
    tripLabel: String = "TRIP B",
    tripValue: String = "0.0",
    colors: SpeedGaugeColors = SpeedGaugeDefaults.colors(),
) {
    val animatedSpeed by animateFloatAsState(
        targetValue = speedKmh.coerceIn(0f, MAX_SPEED_KMH),
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 60f),
        label = "needle",
    )
    val textMeasurer = rememberTextMeasurer()
    Column(modifier = modifier.background(colors.background)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            drawGaugeDial(textMeasurer, colors)
            drawNeedle(animatedSpeed, colors)
        }
        TripComputerStrip(
            outsideTempC = outsideTempC,
            tripLabel = tripLabel,
            tripValue = tripValue,
            colors = colors,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
        )
    }
}

private fun DrawScope.drawGaugeDial(textMeasurer: TextMeasurer, colors: SpeedGaugeColors) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = min(size.width, size.height) / 2f * GAUGE_MARGIN_FACTOR

    // Outer copper bezel.
    drawArc(
        color = colors.bezelOuter,
        startAngle = START_ANGLE_DEG,
        sweepAngle = SWEEP_DEG,
        useCenter = false,
        topLeft = Offset(cx - radius, cy - radius),
        size = Size(radius * 2f, radius * 2f),
        style = Stroke(width = radius * 0.035f),
    )
    // Thin bright inner edge like the reference cluster.
    drawArc(
        color = colors.bezelInner,
        startAngle = START_ANGLE_DEG,
        sweepAngle = SWEEP_DEG,
        useCenter = false,
        topLeft = Offset(cx - radius * 0.965f, cy - radius * 0.965f),
        size = Size(radius * 1.93f, radius * 1.93f),
        style = Stroke(width = radius * 0.008f),
    )

    val majorTickOuter = radius * 0.95f
    val majorTickInner = radius * 0.83f
    val minorTickOuter = radius * 0.95f
    val minorTickInner = radius * 0.89f
    val labelRadius = radius * 0.68f

    var speed = 0f
    while (speed <= MAX_SPEED_KMH) {
        val rad = speedToAngle(speed).toRadians()
        val isMajor = (speed % 20f) == 0f
        val inner = if (isMajor) majorTickInner else minorTickInner
        val outer = if (isMajor) majorTickOuter else minorTickOuter
        drawLine(
            color = if (isMajor) colors.majorTick else colors.minorTick,
            start = Offset(cx + inner * cos(rad), cy + inner * sin(rad)),
            end = Offset(cx + outer * cos(rad), cy + outer * sin(rad)),
            strokeWidth = if (isMajor) radius * 0.028f else radius * 0.016f,
            cap = StrokeCap.Butt,
        )
        speed += 10f
    }

    // Numerals 0..260 every 20.
    val numeralStyle = TextStyle(
        color = colors.numeral,
        fontSize = (radius * 0.11f).toSp(),
        fontWeight = FontWeight.Bold,
    )
    var label = 0
    while (label <= MAX_SPEED_KMH.toInt()) {
        val rad = speedToAngle(label.toFloat()).toRadians()
        val layout = textMeasurer.measure(label.toString(), numeralStyle)
        drawText(
            textLayoutResult = layout,
            topLeft = Offset(
                cx + labelRadius * cos(rad) - layout.size.width / 2f,
                cy + labelRadius * sin(rad) - layout.size.height / 2f,
            ),
        )
        label += 20
    }
}

private fun DrawScope.drawNeedle(speedKmh: Float, colors: SpeedGaugeColors) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = min(size.width, size.height) / 2f * GAUGE_MARGIN_FACTOR
    val center = Offset(cx, cy)
    val angleRad = speedToAngle(speedKmh).toRadians()
    val dir = Offset(cos(angleRad), sin(angleRad))
    val tip = center + dir * radius * 0.78f
    val tail = center - dir * radius * 0.18f

    // Dark under-stroke for the glowing-plastic look.
    drawLine(
        color = colors.needleShadow,
        start = tail,
        end = tip,
        strokeWidth = radius * 0.055f,
        cap = StrokeCap.Round,
    )
    drawLine(
        color = colors.needle,
        start = tail,
        end = tip,
        strokeWidth = radius * 0.032f,
        cap = StrokeCap.Round,
    )
    drawCircle(color = colors.hub, radius = radius * 0.075f, center = center)
    drawCircle(
        color = colors.needle,
        radius = radius * 0.075f,
        center = center,
        style = Stroke(width = radius * 0.015f),
    )
    drawCircle(color = colors.hubCenter, radius = radius * 0.03f, center = center)
}

@Composable
private fun TripComputerStrip(
    outsideTempC: String,
    tripLabel: String,
    tripValue: String,
    colors: SpeedGaugeColors,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(colors.stripBackground, RoundedCornerShape(4.dp))
            .padding(horizontal = 18.dp, vertical = 6.dp),
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Column {
                StripText(text = "OUTSIDE", color = colors.stripLabel, fontSize = 9.sp)
                StripText(text = outsideTempC, color = colors.stripValue, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(28.dp))
            Column {
                StripText(text = "TRIP", color = colors.stripLabel, fontSize = 9.sp)
                StripText(
                    text = tripLabel.removePrefix("TRIP ").ifEmpty { "B" },
                    color = colors.stripLabel,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.width(28.dp))
            StripText(text = tripValue, color = colors.stripValue, fontSize = 22.sp)
        }
    }
}

@Composable
private fun StripText(text: String, color: Color, fontSize: TextUnit) {
    BasicText(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
    )
}
