package com.forasoft.androidutils.ui.compose.behavior

import kotlinx.coroutines.flow.StateFlow

/**
 * An interface that describes the logic of behavior management.
 *
 * Designed to control the current [Behavior].
 *
 * Example:
 * ```
 * sealed interface BottomBarBehavior : Behavior {
 *     data object Visible : BottomBarBehavior
 *     data object Hidden : BottomBarBehavior
 * }
 *
 * typealias BottomBarBehaviorController = BehaviorController<BottomBarBehavior>
 *
 * val LocalBottomBarBehaviorController =
 *     staticCompositionLocalOf<BottomBarBehaviorController> {
 *         val defaultBehavior = BottomBarBehavior.Hidden
 *         NoopBehaviorController(defaultBehavior)
 *     }
 *
 * @Composable
 * fun ForcedBottomBarBehavior(behavior: BottomBarBehavior) {
 *     val controller = LocalBottomBarBehaviorController.current
 *     DisposableEffect(behavior, controller) {
 *         controller.pushBehavior(behavior)
 *         onDispose { controller.popBehavior(behavior) }
 *     }
 * }
 *
 * @Composable
 * fun AppComposable() {
 *     val bottomBarBehaviorController: BehaviorController<BottomBarBehavior> =
 *         remember {
 *             val defaultBehavior = BottomBarBehavior.Hidden
 *             DefaultBehaviorController(defaultBehavior)
 *         }
 *
 *         CompositionLocalProvider(
 *             LocalBottomBarBehaviorController provides bottomBarBehaviorController,
 *         ) {
 *            val bottomBarBehavior by
 *                bottomBarBehaviorController.currentBehavior.collectAsStateWithLifecycle()
 *
 *             Scaffold(
 *                 bottomBar = {
 *                     when (bottomBarBehavior) {
 *                         BottomBarBehavior.Visible -> {} // show bottom bar
 *                         BottomBarBehavior.Hidden -> {} // hide bottom bar
 *                     }
 *                 },
 *             ) {
 *                 // App content
 *             }
 *         }
 * }
 *
 * @Composable
 * fun ScreenWithBottomBar() {
 *     ForcedBottomBarBehavior(behavior = BottomBarBehavior.Visible)
 *     // Screen content
 * }
 *
 * @Composable
 * fun ScreenWithoutBottomBar() {
 *     ForcedBottomBarBehavior(behavior = BottomBarBehavior.Hidden)
 *     // Screen content
 * }
 * ```
 *
 * @param T defines the type of behavior to be used.
 */
public interface BehaviorController<T : Behavior> {
    /**
     * The current [Behavior], represented as a [StateFlow].
     */
    public val currentBehavior: StateFlow<T>

    /**
     * A method to set the default behavior.
     *
     * @param behavior [Behavior] to be set as the default.
     */
    public fun setDefaultBehavior(behavior: T)

    /**
     * A method to add a behavior to the stack.
     *
     * @param behavior [Behavior] to add to the stack.
     */
    public fun pushBehavior(behavior: T)

    /**
     * A method to remove a behavior from the stack.
     *
     * @param behavior [Behavior] to remove from the stack.
     */
    public fun popBehavior(behavior: T)
}
