package com.forasoft.androidutils.ui.compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

/**
 * An abstraction for a Compose Navigation destination that encapsulates destination's
 * [routeSchema], [arguments] and [deepLinks].
 *
 * @param P type of destination's arguments.
 * @property routeSchema string that can be used as a route schema for a destination.
 */
abstract class Destination<P>(val routeSchema: String) {

    /**
     * Returns a string that can be used as a route for NavController navigation.
     *
     * @param args destination's arguments.
     */
    @Suppress("Unused")
    abstract fun createRoute(args: P): String

    /**
     * List of arguments associated with the destination.
     */
    open val arguments: List<NamedNavArgument>
        get() = emptyList()

    /**
     * List of deep links associated with the destinations.
     */
    open val deepLinks: List<NavDeepLink>
        get() = emptyList()

}
