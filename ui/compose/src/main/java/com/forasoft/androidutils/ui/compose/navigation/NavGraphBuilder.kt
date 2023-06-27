package com.forasoft.androidutils.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation

/**
 * Adds the [Destination] to the [NavGraphBuilder].
 *
 * @param destination destination to add.
 * @param content composable for the destination.
 */
fun NavGraphBuilder.composableDestination(
    destination: Destination<*>,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.routeSchema,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        content = content,
    )
}

/**
 * Adds the [Destination] to the [NavGraphBuilder] that will be hosted within a
 * [androidx.compose.ui.window.Dialog].
 *
 * This is suitable only when this dialog represents
 * a separate screen in your app that needs its own lifecycle and saved state, independent
 * of any other destination in your navigation graph. For use cases such as `AlertDialog`,
 * you should use those APIs directly in the [composableDestination] destination that
 * wants to show that dialog.
 *
 * @param destination destination to add.
 * @param dialogProperties properties that should be passed to [androidx.compose.ui.window.Dialog].
 * @param content composable content for the destination that will be hosted within the Dialog.
 */
fun NavGraphBuilder.dialogDestination(
    destination: Destination<*>,
    dialogProperties: DialogProperties = DialogProperties(),
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    dialog(
        route = destination.routeSchema,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        dialogProperties = dialogProperties,
        content = content,
    )
}

/**
 * Constructors a nested [NavGraph] based on the given [Graph].
 *
 * @param builder the builder used to construct the graph.
 */
inline fun NavGraphBuilder.navigationGraph(graph: Graph<*>, builder: NavGraphBuilder.() -> Unit) {
    navigation(
        route = graph.routeSchema,
        startDestination = graph.startDestination.routeSchema,
        builder = builder,
    )
}
