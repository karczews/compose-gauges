package io.github.karczews.gauges.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

public fun gaugeDemos(): List<GaugeDemo> = listOf(SpeedGaugeDemo)

@Composable
public fun Gallery(modifier: Modifier = Modifier) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val demos = remember { gaugeDemos() }
    MaterialTheme(colorScheme = darkColorScheme()) {
        val selected = demos.firstOrNull { it.id == selectedId }
        if (selected == null) {
            GalleryList(demos = demos, onOpen = { selectedId = it.id }, modifier = modifier)
        } else {
            GaugeDetail(demo = selected, onBack = { selectedId = null }, modifier = modifier)
        }
    }
}

@Composable
private fun GalleryList(demos: List<GaugeDemo>, onOpen: (GaugeDemo) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "Compose Gauges",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 4.dp),
            )
        }
        items(demos, key = { it.id }) { demo ->
            Card(onClick = { onOpen(demo) }, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = demo.title, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = demo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun GaugeDetail(demo: GaugeDemo, onBack: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        TextButton(onClick = onBack, modifier = Modifier.padding(8.dp)) {
            Text(text = "← All gauges")
        }
        demo.Content(modifier = Modifier.weight(1f).fillMaxWidth())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun GalleryPreview() {
    Gallery()
}
