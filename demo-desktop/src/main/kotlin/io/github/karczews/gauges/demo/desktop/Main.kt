package io.github.karczews.gauges.demo.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.karczews.gauges.demo.gallery.Gallery

fun main() = application {
    Window(title = "Compose Gauges", onCloseRequest = ::exitApplication) {
        Gallery()
    }
}
