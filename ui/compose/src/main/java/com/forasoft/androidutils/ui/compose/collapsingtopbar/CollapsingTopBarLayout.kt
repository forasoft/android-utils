package com.forasoft.androidutils.ui.compose.collapsingtopbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout

// Source: androidx.compose.material3.Scaffold

/**
 * Layout that implements a collapsing top bar behavior and allows the top bar to change
 * its appearance as the content scrolls.
 *
 * @param topBar top bar of the screen.
 * @param scrollBehavior [TopBarScrollBehavior] which holds various offset values that will be
 * applied by this top bar to set up its height. A scroll behavior is designed to
 * work in conjunction with a scrolled content to change the top app bar appearance as the content
 * scrolls. See [TopBarScrollBehavior.nestedScrollConnection].
 * @param modifier [Modifier] to be applied to this layout.
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root.
 */
@Composable
fun CollapsingTopBarLayout(
    topBar: @Composable () -> Unit,
    scrollBehavior: TopBarScrollBehavior,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val topBarPlaceables = subcompose(LayoutContent.TopBar) {
                CollapsingTopBar(
                    scrollBehavior = scrollBehavior,
                    content = topBar,
                )
            }.map { it.measure(looseConstraints) }
            val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0

            val contentPlaceables = subcompose(LayoutContent.MainContent) {
                val innerPadding = PaddingValues(top = topBarHeight.toDp())
                content(innerPadding)
            }.map { it.measure(looseConstraints) }

            contentPlaceables.forEach { it.place(0, 0) }
            topBarPlaceables.forEach { it.place(0, 0) }
        }
    }
}

private enum class LayoutContent {
    MainContent, TopBar
}
