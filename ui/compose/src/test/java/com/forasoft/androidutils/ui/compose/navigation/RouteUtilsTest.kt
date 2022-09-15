package com.forasoft.androidutils.ui.compose.navigation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RouteUtilsTest {

    @Test
    fun generateRouteSchema_withoutArgs_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema("events")
        assertThat(routeSchema).isEqualTo("events")
    }

    @Test
    fun generateRouteSchema_withOneArg_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            args = arrayOf("id"),
        )
        assertThat(routeSchema).isEqualTo("events/{id}")
    }

    @Test
    fun generateRouteSchema_withMultipleArgs_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            args = arrayOf("firstId", "secondId"),
        )
        assertThat(routeSchema).isEqualTo("events/{firstId}/{secondId}")
    }

    @Test
    fun generateRouteSchema_withOneOptionalArg_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            optionalArgs = arrayOf("id"),
        )
        assertThat(routeSchema).isEqualTo("events?id={id}")
    }

    @Test
    fun generateRouteSchema_withMultipleOptionalArgs_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            optionalArgs = arrayOf("firstId", "secondId"),
        )
        assertThat(routeSchema).isEqualTo("events?firstId={firstId}&secondId={secondId}")
    }

    @Test
    fun generateRouteSchema_withOneArgAndOneOptionalArg_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            args = arrayOf("id"),
            optionalArgs = arrayOf("optionalId"),
        )
        assertThat(routeSchema).isEqualTo("events/{id}?optionalId={optionalId}")
    }

    @Test
    fun generateRouteSchema_withMultipleArgsAndOneOptionalArg_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            args = arrayOf("firstId", "secondId"),
            optionalArgs = arrayOf("optionalId"),
        )
        assertThat(routeSchema)
            .isEqualTo("events/{firstId}/{secondId}?optionalId={optionalId}")
    }

    @Test
    fun generateRouteSchema_withMultipleArgsAndMultipleOptionalArgs_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            args = arrayOf("firstId", "secondId"),
            optionalArgs = arrayOf("firstOptionalId", "secondOptionalId"),
        )
        assertThat(routeSchema).isEqualTo(
            "events/{firstId}/{secondId}?firstOptionalId={firstOptionalId}&secondOptionalId={secondOptionalId}"
        )
    }

    @Test
    fun generateRouteSchema_withOneArgsAndMultipleOptionalArgs_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events",
            args = arrayOf("id"),
            optionalArgs = arrayOf("firstOptionalId", "secondOptionalId"),
        )
        assertThat(routeSchema).isEqualTo(
            "events/{id}?firstOptionalId={firstOptionalId}&secondOptionalId={secondOptionalId}"
        )
    }

    @Test
    fun generateRouteSchema_withRouteBaseWithClosingSlashAndArgs_returnsCorrectString() {
        val routeSchema = RouteUtils.generateRouteSchema(
            routeBase = "events/stream/",
            args = arrayOf("id"),
            optionalArgs = arrayOf("firstOptionalId"),
        )
        assertThat(routeSchema).isEqualTo(
            "events/stream/{id}?firstOptionalId={firstOptionalId}"
        )
    }

    @Test
    fun generateRoute_withoutArgs_returnsCorrectString() {
        val route = RouteUtils.generateRoute("events")
        assertThat(route).isEqualTo("events")
    }

    @Test
    fun generateRoute_withOneArg_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            args = arrayOf(1),
        )
        assertThat(route).isEqualTo("events/1")
    }

    @Test
    fun generateRoute_withMultipleArgs_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            args = arrayOf(1, "2"),
        )
        assertThat(route).isEqualTo("events/1/2")
    }

    @Test
    fun generateRoute_withOneOptionalArg_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            optionalArgs = arrayOf(OptionalNavArg("id", 1)),
        )
        assertThat(route).isEqualTo("events?id=1")
    }

    @Test
    fun generateRoute_withMultipleOptionalArgs_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            optionalArgs = arrayOf(
                OptionalNavArg("firstId", 1),
                OptionalNavArg("secondId", "2"),
            ),
        )
        assertThat(route).isEqualTo("events?firstId=1&secondId=2")
    }

    @Test
    fun generateRoute_withOneArgAndOneOptionalArg_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            args = arrayOf("firstRequired"),
            optionalArgs = arrayOf(OptionalNavArg("optional", 1)),
        )
        assertThat(route).isEqualTo("events/firstRequired?optional=1")
    }

    @Test
    fun generateRoute_withMultipleArgsAndOneOptionalArg_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            args = arrayOf("firstRequired", "secondRequired"),
            optionalArgs = arrayOf(OptionalNavArg("optional", 1)),
        )
        assertThat(route).isEqualTo("events/firstRequired/secondRequired?optional=1")
    }

    @Test
    fun generateRoute_withMultipleArgsAndMultipleOptionalArgs_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            args = arrayOf("firstRequired", "secondRequired"),
            optionalArgs = arrayOf(
                OptionalNavArg("firstOptional", 22),
                OptionalNavArg("secondOptional", "44"),
            ),
        )
        assertThat(route).isEqualTo(
            "events/firstRequired/secondRequired?firstOptional=22&secondOptional=44"
        )
    }

    @Test
    fun generateRoute_withOneArgAndMultipleOptionalArgs_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events",
            args = arrayOf("required"),
            optionalArgs = arrayOf(
                OptionalNavArg("firstOptional", 22),
                OptionalNavArg("secondOptional", "44"),
            ),
        )
        assertThat(route).isEqualTo(
            "events/required?firstOptional=22&secondOptional=44"
        )
    }

    @Test
    fun generateRoute_withRouteBaseWithClosingSlashAndArgs_returnsCorrectString() {
        val route = RouteUtils.generateRoute(
            routeBase = "events/stream/",
            args = arrayOf("required"),
            optionalArgs = arrayOf(
                OptionalNavArg("optional", 22),
            ),
        )
        assertThat(route).isEqualTo(
            "events/stream/required?optional=22"
        )
    }

}
