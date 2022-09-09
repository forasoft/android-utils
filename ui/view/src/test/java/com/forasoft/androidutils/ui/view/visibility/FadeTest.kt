package com.forasoft.androidutils.ui.view.visibility

import android.animation.Animator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@RunWith(RobolectricTestRunner::class)
class FadeTest {

    private val context: Context
        get() = RuntimeEnvironment.getApplication()

    private val animationDuration = 200.milliseconds
    private val rightBeforeAnimationEnd = animationDuration - 1.milliseconds
    private val rightAfterAnimationEnd = animationDuration + 1.milliseconds

    private lateinit var visibleView: View
    private lateinit var goneView: View

    @Before
    fun before() {
        visibleView = View(context).apply {
            visibility = View.VISIBLE
            alpha = 1f
        }
        goneView = View(context).apply {
            visibility = View.GONE
            alpha = 0f
        }
    }

    @Test
    fun fade_showsView() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }

        goneView.fade(isVisible = true, duration = animationDuration, listener = listener)

        with(goneView) {
            checkRightBeforeAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            checkRightAfterAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isEqualTo(1f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fade_hidesView() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }

        visibleView.fade(
            isVisible = false,
            duration = animationDuration,
            listener = listener,
        )

        with(visibleView) {
            checkRightBeforeAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            checkRightAfterAnimationEnd {
                assertThat(visibility).isEqualTo(View.GONE)
                assertThat(alpha).isEqualTo(0f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fade_setsVisibilityToInvisible() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }

        visibleView.fade(
            isVisible = false,
            duration = animationDuration,
            fadeOutTargetVisibility = View.INVISIBLE,
            listener = listener,
        )

        with(visibleView) {
            checkRightBeforeAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            checkRightAfterAnimationEnd {
                assertThat(visibility).isEqualTo(View.INVISIBLE)
                assertThat(alpha).isEqualTo(0f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fade_whenCurrentAlphaIsBetweenZeroAndOne_animationDurationShouldBeAppropriate() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }
        val initialProgress = 0.7f
        visibleView.alpha = initialProgress

        visibleView.fade(isVisible = true, duration = animationDuration, listener = listener)

        with(visibleView) {
            val expectedDuration = animationDuration.inWholeMilliseconds * (1 - initialProgress)
            val rightBeforeAnimationEnd = expectedDuration - 5 // Not 1 because of rounding
            val rightAfterAnimationEnd = expectedDuration + 5

            postDelayed(rightBeforeAnimationEnd.roundToInt().milliseconds){
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            postDelayed(rightAfterAnimationEnd.roundToInt().milliseconds){
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isEqualTo(1f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fadeIn_showsView() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }

        goneView.fadeIn(duration = animationDuration, listener = listener)

        with(goneView) {
            checkRightBeforeAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            checkRightAfterAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isEqualTo(1f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fadeIn_whenCurrentAlphaIsBetweenZeroAndOne_animationDurationShouldBeAppropriate() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }
        val initialProgress = 0.7f
        visibleView.alpha = initialProgress

        visibleView.fadeIn(duration = animationDuration, listener = listener)

        with(visibleView) {
            val expectedDuration = animationDuration.inWholeMilliseconds * (1 - initialProgress)
            val rightBeforeAnimationEnd = expectedDuration - 5 // Not 1 because of rounding
            val rightAfterAnimationEnd = expectedDuration + 5

            postDelayed(rightBeforeAnimationEnd.roundToInt().milliseconds){
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            postDelayed(rightAfterAnimationEnd.roundToInt().milliseconds){
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isEqualTo(1f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fadeOut_hidesView() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }

        visibleView.fadeOut(duration = animationDuration, listener = listener)

        with(visibleView) {
            checkRightBeforeAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            checkRightAfterAnimationEnd {
                assertThat(visibility).isEqualTo(View.GONE)
                assertThat(alpha).isEqualTo(0f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fadeOut_setsVisibilityToInvisible() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }

        visibleView.fadeOut(
            duration = animationDuration,
            targetVisibility = View.INVISIBLE,
            listener = listener,
        )

        with(visibleView) {
            checkRightBeforeAnimationEnd {
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            checkRightAfterAnimationEnd {
                assertThat(visibility).isEqualTo(View.INVISIBLE)
                assertThat(alpha).isEqualTo(0f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Test
    fun fadeOut_whenCurrentAlphaIsBetweenZeroAndOne_animationDurationShouldBeAppropriate() {
        var isCompleted = false
        val listener = onAnimationEndListener { isCompleted = true }
        val initialProgress = 0.3f
        visibleView.alpha = 1 - initialProgress

        visibleView.fadeOut(duration = animationDuration, listener = listener)

        with(visibleView) {
            val expectedDuration = animationDuration.inWholeMilliseconds * (1 - initialProgress)
            val rightBeforeAnimationEnd = expectedDuration - 5 // Not 1 because of rounding
            val rightAfterAnimationEnd = expectedDuration + 5

            postDelayed(rightBeforeAnimationEnd.roundToInt().milliseconds){
                assertThat(visibility).isEqualTo(View.VISIBLE)
                assertThat(alpha).isNotEqualTo(1f)
                assertThat(alpha).isNotEqualTo(0f)
            }
            postDelayed(rightAfterAnimationEnd.roundToInt().milliseconds){
                assertThat(visibility).isEqualTo(View.GONE)
                assertThat(alpha).isEqualTo(0f)
            }
            Robolectric.flushForegroundThreadScheduler()
            assertThat(isCompleted).isTrue()
        }
    }

    @Suppress("EmptyFunctionBlock")
    private fun onAnimationEndListener(onEnd: () -> Unit): Animator.AnimatorListener {
        return object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                println("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                println("onAnimationEnd")
                onEnd()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        }
    }

    private fun checkRightBeforeAnimationEnd(action: () -> Unit) {
        postDelayed(rightBeforeAnimationEnd) {
            println("Right before animation end")
            action()
        }
    }

    private fun checkRightAfterAnimationEnd(action: () -> Unit) {
        postDelayed(rightAfterAnimationEnd) {
            println("Right after animation end")
            action()
        }
    }

    private fun postDelayed(duration: Duration, runnable: Runnable) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, duration.inWholeMilliseconds)
    }

}
