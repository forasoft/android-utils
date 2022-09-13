package com.forasoft.androidutils.ui.view.visibility

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VisibilityKtTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var view: TextView

    @Before
    fun before() {
        view = TextView(context)
    }

    @Test
    fun showImmediately_showsView() {
        view.apply {
            visibility = View.GONE
            alpha = 0f
        }

        view.showImmediately()

        assertThat(view.visibility).isEqualTo(View.VISIBLE)
        assertThat(view.alpha).isEqualTo(1f)
    }

    @Test
    fun hideImmediately_setsVisibilityToGone() {
        view.hideImmediately(targetVisibility = View.GONE)

        assertThat(view.visibility).isEqualTo(View.GONE)
    }

    @Test
    fun hideImmediately_setsVisibilityToInvisible() {
        view.hideImmediately(targetVisibility = View.INVISIBLE)

        assertThat(view.visibility).isEqualTo(View.INVISIBLE)
    }

    @Test
    fun hideImmediately_setsAlphaToZero() {
        view.hideImmediately()

        assertThat(view.alpha).isEqualTo(0f)
    }

}
