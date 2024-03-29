package com.forasoft.androidutils.ui.compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.forasoft.androidutils.ui.compose.navigation.parameterless.SimpleDestination

/**
 * An abstraction for a Compose Navigation destination that encapsulates destination's
 * [routeSchema], [arguments] and [deepLinks].
 *
 * Use parameterless version [SimpleDestination] if arguments not needed.
 *
 * @param P type of destination's arguments.
 * @see [SimpleDestination].
 */
public abstract class Destination<P> {

    /**
     * String that can be used as a route schema for a destination.
     */
    public abstract val routeSchema: String

    /**
     * Returns a string that can be used as a route for NavController navigation.
     *
     * @param args destination's arguments.
     */
    public abstract fun createRoute(args: P): String

    /**
     * List of arguments associated with the destination.
     */
    public open val arguments: List<NamedNavArgument>
        get() = emptyList()

    /**
     * List of deep links associated with the destinations.
     */
    public open val deepLinks: List<NavDeepLink>
        get() = emptyList()

}
