package io.github.karczews.gauges.speed

import androidx.compose.ui.graphics.Color

/** Default values used by [SpeedGauge]. */
public object SpeedGaugeDefaults {
    /** Highest speed shown on the dial, in km/h. */
    public const val MAX_SPEED_KMH: Float = 260f

    /** Classic amber cluster look. */
    public fun colors(
        background: Color = Color(0xFF000000),
        bezelOuter: Color = Color(0xFF7A3E14),
        bezelInner: Color = Color(0xFFFF9A2A),
        majorTick: Color = Color(0xFFFFFFFF),
        minorTick: Color = Color(0xFFCC6A1A),
        numeral: Color = Color(0xFFFFFFFF),
        needleShadow: Color = Color(0xFF5A1E00),
        needle: Color = Color(0xFFFF8C00),
        hub: Color = Color(0xFF1A1A1A),
        hubCenter: Color = Color(0xFF000000),
        stripBackground: Color = Color(0xFF141414),
        stripLabel: Color = Color(0xFF9A9A9A),
        stripValue: Color = Color(0xFFFFFFFF),
    ): SpeedGaugeColors = SpeedGaugeColors(
        background = background,
        bezelOuter = bezelOuter,
        bezelInner = bezelInner,
        majorTick = majorTick,
        minorTick = minorTick,
        numeral = numeral,
        needleShadow = needleShadow,
        needle = needle,
        hub = hub,
        hubCenter = hubCenter,
        stripBackground = stripBackground,
        stripLabel = stripLabel,
        stripValue = stripValue,
    )
}
