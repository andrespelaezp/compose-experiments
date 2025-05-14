package com.andrespelaezp.composeexperiments.experiments.toggle_bubble

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SpringyToggle(
    isOn: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val transition = updateTransition(targetState = isOn, label = "toggle")
    val offset by transition.animateDp(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "offset"
    ) { if (it) 30.dp else 0.dp }

    Box(
        Modifier
            .width(60.dp)
            .height(30.dp)
            .background(Color.Gray, RoundedCornerShape(15.dp))
            .clickable { onToggle(!isOn) }
            .padding(4.dp)
    ) {
        Box(
            Modifier
                .offset(x = offset)
                .size(22.dp)
                .background(if (isOn) Color.Green else Color.Red, CircleShape)
        )
    }
}

@Composable
fun MarkdownBubble(content: String) {
    val parsed = remember(content) { MarkdownParser.parse(content) }
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF222222),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            parsed,
            color = Color.White,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Stub for MarkdownParser (replace with actual markdown renderer like Markdown or Compose Markdown)
object MarkdownParser {
    fun parse(markdown: String): AnnotatedString = buildAnnotatedString {
        append(markdown) // Simulated markdown
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayground() {
    var isToggled by remember { mutableStateOf(false) }
    Column(Modifier.padding(16.dp)) {
        Text("Springy Toggle")
        SpringyToggle(isToggled) { isToggled = it }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Markdown Bubble")
        MarkdownBubble("**Hello!** This is a _Markdown_ bubble.`inline code`.")
    }
}
