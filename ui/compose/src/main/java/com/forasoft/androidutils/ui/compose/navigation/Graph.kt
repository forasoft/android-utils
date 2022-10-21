package com.forasoft.androidutils.ui.compose.navigation

/**
 * An abstraction for a Compose Navigation nested graph that encapsulates graph's
 * [routeSchema] and [startDestination].
 *
 * @param P type of graph's arguments.
 */
abstract class Graph<P> : Destination<P>() {

    /**
     * String that can be used as a route schema for a nested graph.
     */
    abstract override val routeSchema: String

    /**
     * Returns a string that can be used as a route for NavController navigation.
     *
     * @param args graph's arguments.
     */
    abstract override fun createRoute(args: P): String

    /**
     * Graph's start [Destination].
     */
    abstract val startDestination: Destination<*>

}
