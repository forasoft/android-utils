package com.forasoft.androidutils.ui.compose.navigation.parameterless

import com.forasoft.androidutils.ui.compose.navigation.Graph

/**
 * An abstraction for a Compose Navigation nested graph that encapsulates graph's
 * [routeSchema] and [startDestination].
 *
 * Parameterless version of [Graph].
 *
 * @see [Graph].
 */
@Suppress("Unused")
abstract class Graph : Graph<Unit>() {

    abstract override val routeSchema: String

    /**
     * String that can be used as a route for NavController navigation.
     */
    val route: String get() = routeSchema

    @Deprecated(
        message = "Use 'route' property instead",
        replaceWith = ReplaceWith("route"),
        level = DeprecationLevel.WARNING,
    )
    override fun createRoute(args: Unit): String = route

}
