package com.forasoft.androidutils.ui.view.visibility

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@RunWith(AndroidJUnit4::class)
class FadeTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var view: TextView

    private lateinit var handler: Handler

    private val duration = 200.milliseconds

    @Before
    fun before() {
        view = TextView(context)
        handler = Handler(Looper.getMainLooper())
    }

    @Test
    fun fade_showsView() {
        view.apply {
            visibility = View.GONE
            alpha = 0f
        }

        view.fade(isVisible = true, duration)

        handler.postDelayed(duration) {
            assertThat(view.visibility).isEqualTo(View.VISIBLE)
            assertThat(view.alpha).isEqualTo(1f)
        }
    }

    @Test
    fun fade_hidesView() {
        view.fade(isVisible = false, duration)

        handler.postDelayed(duration) {
            assertThat(view.visibility).isEqualTo(View.GONE)
            assertThat(view.alpha).isEqualTo(0f)
        }
    }

    @Test
    fun fade_setsVisibilityToInvisible() {
        view.fade(isVisible = false, duration = duration, fadeOutTargetVisibility = View.INVISIBLE)

        handler.postDelayed(duration) {
            assertThat(view.visibility).isEqualTo(View.INVISIBLE)
            assertThat(view.alpha).isEqualTo(0f)
        }
    }

    @Test
    fun fadeIn_showsView() {
        view.apply {
            visibility = View.GONE
            alpha = 0f
        }

        view.fadeIn(duration)

        handler.postDelayed(duration) {
            assertThat(view.visibility).isEqualTo(View.VISIBLE)
            assertThat(view.alpha).isEqualTo(1f)
        }
    }

    @Test
    fun fadeOut_hidesView() {
        view.fadeOut(duration)

        handler.postDelayed(duration) {
            assertThat(view.visibility).isEqualTo(View.GONE)
            assertThat(view.alpha).isEqualTo(0f)
        }
    }

    @Test
    fun fadeOut_setsVisibilityToGone() {
        view.fadeOut(duration, targetVisibility = View.INVISIBLE)

        handler.postDelayed(duration) {
            assertThat(view.visibility).isEqualTo(View.INVISIBLE)
            assertThat(view.alpha).isEqualTo(0f)
        }
    }

    private fun Handler.postDelayed(delay: Duration, runnable: Runnable) {
        postDelayed(runnable, delay.inWholeMilliseconds)
    }

}
