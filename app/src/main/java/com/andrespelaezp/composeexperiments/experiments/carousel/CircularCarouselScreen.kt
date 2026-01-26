package com.andrespelaezp.composeexperiments.experiments.carousel

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrespelaezp.composeexperiments.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun CircularCarouselScreen() {
    val coroutineScope = rememberCoroutineScope()
    val developers = getDevelopers()
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val screenDimensions = remember(windowInfo, density) {
        ScreenDimensions(
            width = windowInfo.containerSize.width.dp / density.density,
            height = windowInfo.containerSize.height.dp / density.density
        )
    }

    val carouselConfig = remember(screenDimensions) { CarouselConfig.from(screenDimensions) }
    val radiusPx = with(density) { carouselConfig.radiusDp.toPx() }
    val totalItems = Int.MAX_VALUE
    val startIndex = totalItems / 2

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    var currentDeveloper by remember { mutableStateOf<ComposeDeveloper?>(null) }
    var centerItemIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    val snapTolerancePx = carouselConfig.itemSize.value
    val snapToleranceRange = -snapTolerancePx..snapTolerancePx
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    LaunchedEffect(configuration.orientation) {
        // On orientation change, scroll to the center item to avoid misplacement
        lazyListState.scrollToItem(index = centerItemIndex ?: startIndex)
    }

    LaunchedEffect(Unit) {
        snapshotFlow { lazyListState.layoutInfo }
            .collect { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                if (visibleItems.isNotEmpty()) {
                    val centerItem = visibleItems.find { it.offset.toFloat() in snapToleranceRange }
                    centerItemIndex = centerItem?.index

                    centerItem?.let { item ->
                        val rawIndex = item.index - startIndex + 1
                        val safeIndex = rawIndex.mod(developers.size)
                        currentDeveloper = developers[safeIndex]
                    }
                }
            }
    }

    val handlePageDirection: (CarouselDirection) -> Unit = { direction ->
        centerItemIndex?.let { index ->
            coroutineScope.launch {
                val target = when (direction) {
                    CarouselDirection.NEXT -> index + 1
                    CarouselDirection.PREV -> index - 1
                }

                lazyListState.animateScrollToItem(target)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TechBlack)
            .drawBehind {
                val circleSize = screenDimensions.shortestEdge.toPx() * 1.5f
                val centerX = size.width / 2f
                val centerY = size.center.y + (circleSize / 4f)

                val gradient = Brush.verticalGradient(
                    colors = listOf(CarouselGradientStart, CarouselGradientEnd),
                    startY = centerY + circleSize / 8f,
                    endY = centerY - (circleSize * 2.5f) / 2f
                )

                drawCircle(
                    brush = gradient,
                    radius = circleSize / 2f,
                    center = Offset(centerX, centerY)
                )
            }
    ) {
        HeaderText(isPortrait)

        CircularCarouselList(
            lazyListState = lazyListState,
            developers = developers,
            totalItems = totalItems,
            startIndex = startIndex,
            itemSize = carouselConfig.itemSize,
            visualItemSize = carouselConfig.visualItemSize,
            radiusPx = radiusPx,
            density = density
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    bottom = with(density) {
                        /*
                        * Place the review section below the carousel items
                        * If there is not enough space, set it to zero
                        * Use safe area insets for better UI
                        */
                        val halfOfScreen = screenDimensions.height.toPx() / 2f
                        val bottomPaddingPx =
                            (halfOfScreen - (carouselConfig.visualItemSize.toPx().div(2)))
                                .div(density.density)
                                .coerceAtLeast(0f)
                        WindowInsets.safeGestures.asPaddingValues()
                            .calculateBottomPadding() + bottomPaddingPx.toDp()
                    }
                )
        ) {
            ReviewSection(
                developer = currentDeveloper,
                onNavigationEvent = handlePageDirection
            )
        }

        ActionButton(isPortrait)
    }
}


@Composable
fun BoxScope.ActionButton(isPortrait: Boolean) {
    Button(
        onClick = { /* no-op */ },
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
        border = BorderStroke(
            width = 2.dp,
            color = AndroidGreen
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = CarouselGradientEnd,
            contentColor = Color.White
        ),
        contentPadding = ButtonDefaults.ContentPadding,
        modifier = Modifier.run {
            safeContentPadding()
                .align(if (isPortrait) Alignment.BottomCenter else Alignment.CenterEnd)
                .padding(
                    end = if (!isPortrait) 24.dp else 0.dp
                )
                .dropShadow(
                    RoundedCornerShape(32.dp),
                    Shadow(
                        radius = 12.dp,
                        color = Green300,
                    )
                )
        }
    ) {
        Text(
            text = "Get Started",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
    }
}

data class ScreenDimensions(
    val width: Dp,
    val height: Dp
) {
    val shortestEdge: Dp get() = minOf(width, height)
}

@Composable
fun ReviewSection(
    developer: ComposeDeveloper?,
    onNavigationEvent: (CarouselDirection) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CarouselControlButton(
                icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = "Previous",
                onClick = { onNavigationEvent(CarouselDirection.PREV) }
            )
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = developer,
                label = "ReviewText",
                transitionSpec = { fadeIn() + slideInVertically() togetherWith slideOutVertically() + fadeOut() }
            ) { currentReviewer ->
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(TechBlack.copy(alpha = 0.25f), CircleShape)
                        .padding(10.dp),
                    text = currentReviewer?.review.orEmpty(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                )
            }
            CarouselControlButton(
                icon = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Next",
                onClick = { onNavigationEvent(CarouselDirection.NEXT) }
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                ),
            text = developer?.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            color = White.copy(alpha = 0.25f),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
    }
}

// All paddings hardcoded for showcase purposes
@Composable
fun BoxScope.HeaderText(isPortrait: Boolean) {
    Text(
        text = buildAnnotatedString {
            append("From\n")
            withStyle(style = SpanStyle(AndroidGreen)) {
                append("Android ")
            }
            append(if (isPortrait) "Devs" else "\nDevs")
        },
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 48.sp,
        color = Color.White,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .align(if (isPortrait) Alignment.TopStart else Alignment.CenterStart)
            .safeContentPadding()
            .padding(
                start = if (!isPortrait) 24.dp else 0.dp,
                end = 24.dp,
                top = if (isPortrait) 72.dp else 0.dp
            ),
        minLines = 2
    )
}

@Composable
fun CarouselControlButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String = "",
    onClick: () -> Unit = {}
) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(containerColor = Black700.copy(alpha = 0.5f))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}