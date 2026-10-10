package io.github.karczews.gauges.speed

import kotlin.test.Test
import kotlin.test.assertEquals

class SpeedToAngleTest {
    @Test
    fun zeroSpeedMapsToStartAngle() {
        assertEquals(START_ANGLE_DEG, speedToAngle(0f))
    }

    @Test
    fun maxSpeedMapsToEndOfSweep() {
        assertEquals(START_ANGLE_DEG + SWEEP_DEG, speedToAngle(SpeedGaugeDefaults.MAX_SPEED_KMH))
    }

    @Test
    fun halfSpeedMapsToMiddleOfSweep() {
        assertEquals(START_ANGLE_DEG + SWEEP_DEG / 2f, speedToAngle(SpeedGaugeDefaults.MAX_SPEED_KMH / 2f))
    }

    @Test
    fun outOfRangeSpeedIsClamped() {
        assertEquals(START_ANGLE_DEG, speedToAngle(-50f))
        assertEquals(START_ANGLE_DEG + SWEEP_DEG, speedToAngle(1000f))
    }
}
