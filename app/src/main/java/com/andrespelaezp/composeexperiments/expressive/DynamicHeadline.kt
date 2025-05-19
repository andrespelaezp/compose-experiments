package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DynamicHeadline(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge, // Our very bold style
        color = MaterialTheme.colorScheme.secondary, // Using a secondary color for pop
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
    )
}

@Preview(showBackground = true, name = "Dynamic Headline Light")
@Composable
fun PreviewDynamicHeadlineLight() {
    MyCoolExpressiveTheme(darkTheme = false) {
        Surface {
            Column {
                DynamicHeadline(text = "AWAKEN")
                Text(
                    text = "Your Digital Experience",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Dynamic Headline Dark")
@Composable
fun PreviewDynamicHeadlineDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface {
            Column {
                DynamicHeadline(text = "CREATE")
                Text(
                    text = "Boldly & Without Limits",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
            }
        }
    }
}