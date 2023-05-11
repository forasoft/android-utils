package com.forasoft.androidutils.ui.compose.collapsingtopbar

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.roundToInt

/**
 * Wrapper for [CollapsingTopBarLayout] top bar content.
 */
@Composable
internal fun CollapsingTopBar(
    scrollBehavior: TopBarScrollBehavior,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var contentHeightPx by remember { mutableStateOf(0) }

    val heightOffsetLimit = -contentHeightPx
    SideEffect {
        if (scrollBehavior.state.heightOffsetLimit != heightOffsetLimit.toFloat()) {
            scrollBehavior.state.heightOffsetLimit = heightOffsetLimit.toFloat()
        }
    }

    val heightPx = LocalDensity.current.run {
        contentHeightPx + scrollBehavior.state.heightOffset
    }

    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        contentHeightPx = placeables.maxByOrNull { it.height }?.height ?: 0

        val layoutHeight = heightPx.roundToInt()
        layout(constraints.maxWidth, layoutHeight) {
            placeables.forEach {
                it.place(x = 0, y = layoutHeight - it.height)
            }
        }
    }
}
