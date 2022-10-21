package com.forasoft.androidutils.ui.compose.navigation.parameterless

import com.forasoft.androidutils.ui.compose.navigation.Destination

/**
 * An abstraction for a Compose Navigation destination that encapsulates destination's
 * [routeSchema], [arguments] and [deepLinks].
 *
 * Parameterless version of [Destination].
 */
@Suppress("Unused")
abstract class Destination : Destination<Unit>() {

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
