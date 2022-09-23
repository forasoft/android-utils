@file:Suppress("Unused")

package com.forasoft.androidutils.ui.compose.navigation

/**
 * Contains helper methods for generating Jetpack Navigation route schemas and routes.
 */
object RouteUtils {

    /**
     * Generates a route schema based on the route unchanging base, names of mandatory
     * arguments and names of optional arguments.
     *
     * Example:
     * ```
     * RouteUtils.generateRouteSchema(
     *     routeBase = "events",
     *     args = arrayOf("id"),
     *     optionalArgs = arrayOf("optionalId"),
     * )
     * > "events/{id}?optionalId={optionalId}"
     * ```
     *
     * Use [generateRoute] if you need a string with parameter values to use as a route for
     * NavController navigation.
     *
     * @param routeBase unchanging beginning of the route.
     * @param args names of mandatory arguments.
     * @param optionalArgs names of optional arguments.
     * @see [generateRoute]
     * @return string that can be used as a route schema for a destination.
     */
    fun generateRouteSchema(
        routeBase: String,
        args: Array<String> = emptyArray(),
        optionalArgs: Array<String> = emptyArray(),
    ): String = buildString {
        append(routeBase)

        if (args.isNotEmpty()) {
            if (!this.endsWith(MandatoryArgumentsSeparator)) append(MandatoryArgumentsSeparator)
            args.forEachIndexed { index, arg ->
                append("{$arg}")
                if (index < args.lastIndex) append(MandatoryArgumentsSeparator)
            }
        }

        if (optionalArgs.isNotEmpty()) {
            append(MandatoryAndOptionalArgumentsSeparator)
            optionalArgs.forEachIndexed { index, arg ->
                append("$arg={$arg}")
                if (index < optionalArgs.lastIndex) append(OptionalArgumentsSeparator)
            }
        }
    }

    /**
     * Generates a route based on the route unchanging base, values of mandatory arguments
     * and values of optional arguments.
     *
     * Example:
     * ```
     * RouteUtils.generateRoute(
     *     routeBase = "events",
     *     args = arrayOf("firstRequired"),
     *     optionalArgs = arrayOf(OptionalNavArg("optional", 1)),
     * )
     * > "events/firstRequired?optional=1"
     * ```
     *
     * Use [generateRouteSchema] if you need a string with parameter names to use as a
     * route schema for a destination.
     *
     * @param routeBase unchanging beginning of the route.
     * @param args values of mandatory arguments. **The order should be preserved as in
     * the route schema**.
     * @param optionalArgs array of [OptionalNavArg]s.
     * @see [generateRouteSchema]
     * @return string that can be used as a route for NavController navigation.
     */
    fun generateRoute(
        routeBase: String,
        args: Array<Any> = emptyArray(),
        optionalArgs: Array<OptionalNavArg> = emptyArray(),
    ): String = buildString {
        append(routeBase)

        if (args.isNotEmpty()) {
            if (!this.endsWith(MandatoryArgumentsSeparator)) append(MandatoryArgumentsSeparator)
            args.forEachIndexed { index, arg ->
                append("$arg")
                if (index < args.lastIndex) append(MandatoryArgumentsSeparator)
            }
        }

        if (optionalArgs.isNotEmpty()) {
            append(MandatoryAndOptionalArgumentsSeparator)
            optionalArgs.forEachIndexed { index, arg ->
                val (name, value) = arg
                append("$name=$value")
                if (index < optionalArgs.lastIndex) append(OptionalArgumentsSeparator)
            }
        }
    }

    private const val MandatoryArgumentsSeparator = "/"
    private const val MandatoryAndOptionalArgumentsSeparator = "?"
    private const val OptionalArgumentsSeparator = "&"

}
