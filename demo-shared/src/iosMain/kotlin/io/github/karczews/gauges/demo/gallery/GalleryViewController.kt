package io.github.karczews.gauges.demo.gallery

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

public fun GalleryViewController(): UIViewController = ComposeUIViewController { Gallery() }
