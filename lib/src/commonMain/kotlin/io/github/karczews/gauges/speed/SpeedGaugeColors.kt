package io.github.karczews.gauges.speed

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** Colors used to draw a [SpeedGauge]. Defaults are provided by [SpeedGaugeDefaults.colors]. */
@Immutable
public class SpeedGaugeColors(
    public val background: Color,
    public val bezelOuter: Color,
    public val bezelInner: Color,
    public val majorTick: Color,
    public val minorTick: Color,
    public val numeral: Color,
    public val needleShadow: Color,
    public val needle: Color,
    public val hub: Color,
    public val hubCenter: Color,
    public val stripBackground: Color,
    public val stripLabel: Color,
    public val stripValue: Color,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpeedGaugeColors) return false
        return background == other.background &&
            bezelOuter == other.bezelOuter &&
            bezelInner == other.bezelInner &&
            majorTick == other.majorTick &&
            minorTick == other.minorTick &&
            numeral == other.numeral &&
            needleShadow == other.needleShadow &&
            needle == other.needle &&
            hub == other.hub &&
            hubCenter == other.hubCenter &&
            stripBackground == other.stripBackground &&
            stripLabel == other.stripLabel &&
            stripValue == other.stripValue
    }

    override fun hashCode(): Int {
        var result = background.hashCode()
        result = 31 * result + bezelOuter.hashCode()
        result = 31 * result + bezelInner.hashCode()
        result = 31 * result + majorTick.hashCode()
        result = 31 * result + minorTick.hashCode()
        result = 31 * result + numeral.hashCode()
        result = 31 * result + needleShadow.hashCode()
        result = 31 * result + needle.hashCode()
        result = 31 * result + hub.hashCode()
        result = 31 * result + hubCenter.hashCode()
        result = 31 * result + stripBackground.hashCode()
        result = 31 * result + stripLabel.hashCode()
        result = 31 * result + stripValue.hashCode()
        return result
    }
}
