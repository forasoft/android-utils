package com.forasoft.androidutils.ui.compose.navigation.parameterless

import androidx.navigation.NamedNavArgument
import com.forasoft.androidutils.ui.compose.navigation.Graph

/**
 * An abstraction for a Compose Navigation nested graph that encapsulates graph's
 * [routeSchema] and [startDestination].
 *
 * Parameterless version of [Graph].
 *
 * @see [Graph].
 */
public abstract class SimpleGraph : Graph<Unit>() {

    abstract override val routeSchema: String

    /**
     * String that can be used as a route for NavController navigation.
     */
    public val route: String get() = routeSchema

    @Deprecated(
        message = "Use 'route' property instead",
        replaceWith = ReplaceWith("route"),
        level = DeprecationLevel.WARNING,
    )
    override fun createRoute(args: Unit): String = route

    final override val arguments: List<NamedNavArgument>
        get() = super.arguments

}
