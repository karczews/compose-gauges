package io.github.karczews.gauges.demo.gallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public interface GaugeDemo {
    public val id: String
    public val title: String
    public val description: String

    @Composable
    public fun Content(modifier: Modifier = Modifier)
}
