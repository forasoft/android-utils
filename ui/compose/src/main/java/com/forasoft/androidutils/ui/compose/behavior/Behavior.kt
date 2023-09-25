package com.forasoft.androidutils.ui.compose.behavior

/**
 * An interface that describes a behavior of some component.
 *
 * Designed to be used in conjunction with [BehaviorController] to control the current behavior.
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
 */
public interface Behavior
