package com.forasoft.androidutils.ui.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
 * @param enterTransition callback to determine the destination's enter transition
 * @param exitTransition callback to determine the destination's exit transition
 * @param popEnterTransition callback to determine the destination's popEnter transition
 * @param popExitTransition callback to determine the destination's popExit transition
 * @param content composable for the destination.
 */
public fun NavGraphBuilder.composableDestination(
    destination: Destination<*>,
    enterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.routeSchema,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
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
public fun NavGraphBuilder.dialogDestination(
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
public inline fun NavGraphBuilder.navigationGraph(
    graph: Graph<*>,
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = graph.routeSchema,
        startDestination = graph.startDestination.routeSchema,
        builder = builder,
    )
}
