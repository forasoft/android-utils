package com.forasoft.androidutils.ui.view

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.forasoft.androidutils.ui.view.test.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun getString_forEmpty_returnsEmptyString() {
        val text = Text.Empty

        val textString = text.getString(context)

        assertThat(textString).isEmpty()
    }

    @Test
    fun getString_forResource_returnsString() {
        val resId = R.string.string
        val string = context.getString(resId)
        val text = Text.Resource(resId)

        val textString = text.getString(context)

        assertThat(textString).isEqualTo(string)
    }

    @Test
    fun getString_forResourceWithArgs_returnsString() {
        val resId = R.string.string_with_args
        val args = arrayOf("abc", 10)
        val string = context.getString(resId, *args)
        val text = Text.Resource(resId, *args)

        val textString = text.getString(context)

        assertThat(textString).isEqualTo(string)
    }

    @Test
    fun getString_forPluralsResource_returnsString() {
        val resId = R.plurals.plurals_string
        val oneString = context.resources.getQuantityString(resId, 1, 1)
        val oneText = Text.PluralsResource(resId, 1, 1)
        val manyString = context.resources.getQuantityString(resId, 2, 2)
        val manyText = Text.PluralsResource(resId, 2, 2)

        val oneTextString = oneText.getString(context)
        val manyTextString = manyText.getString(context)

        assertThat(oneTextString).isEqualTo(oneString)
        assertThat(manyTextString).isEqualTo(manyString)
    }

    @Test
    fun getString_forString_returnsString() {
        val string = "Text"
        val text = Text.String(string)

        val textString = text.getString(context)

        assertThat(textString).isEqualTo(string)
    }

    @Test
    fun equals_forSameText_returnsTrue() {
        val empty1 = Text.Empty
        val empty2 = Text.Empty
        val resource1 = Text.Resource(R.string.string)
        val resource2 = Text.Resource(R.string.string)
        val pluralsResource1 = Text.PluralsResource(R.plurals.plurals_string, 1, 1)
        val pluralsResource2 = Text.PluralsResource(R.plurals.plurals_string, 1, 1)
        val string1 = Text.String("text")
        val string2 = Text.String("text")

        assertThat(empty1).isEqualTo(empty2)
        assertThat(resource1).isEqualTo(resource2)
        assertThat(pluralsResource1).isEqualTo(pluralsResource2)
        assertThat(string1).isEqualTo(string2)
    }

    @Test
    fun equals_forDifferentText_returnsFalse() {
        val empty = Text.Empty
        val resource1 = Text.Resource(R.string.string)
        val resource2 = Text.Resource(R.string.string_with_args)
        val pluralsResource1 = Text.PluralsResource(R.plurals.plurals_string, 1, 1)
        val pluralsResource2 = Text.PluralsResource(R.plurals.plurals_string, 2, 2)
        val string1 = Text.String("text1")
        val string2 = Text.String("text2")

        assertThat(empty).isNotEqualTo(resource1)
        assertThat(empty).isNotEqualTo(pluralsResource1)
        assertThat(empty).isNotEqualTo(string1)

        assertThat(resource1).isNotEqualTo(resource2)
        assertThat(resource1).isNotEqualTo(pluralsResource1)
        assertThat(resource1).isNotEqualTo(string1)

        assertThat(pluralsResource1).isNotEqualTo(pluralsResource2)
        assertThat(pluralsResource1).isNotEqualTo(string1)

        assertThat(string1).isNotEqualTo(string2)
    }

    @Test
    fun hashCode_forSameText_returnsSameHashCode() {
        val empty1 = Text.Empty
        val empty2 = Text.Empty
        val resource1 = Text.Resource(R.string.string)
        val resource2 = Text.Resource(R.string.string)
        val pluralsResource1 = Text.PluralsResource(R.plurals.plurals_string, 1, 1)
        val pluralsResource2 = Text.PluralsResource(R.plurals.plurals_string, 1, 1)
        val string1 = Text.String("text")
        val string2 = Text.String("text")

        assertThat(empty1.hashCode()).isEqualTo(empty2.hashCode())
        assertThat(resource1.hashCode()).isEqualTo(resource2.hashCode())
        assertThat(pluralsResource1.hashCode()).isEqualTo(pluralsResource2.hashCode())
        assertThat(string1.hashCode()).isEqualTo(string2.hashCode())
    }

    @Test
    fun hashCode_forDifferentText_returnsDifferentHashCode() {
        val emptyHash = Text.Empty.hashCode()
        val resource1Hash = Text.Resource(R.string.string).hashCode()
        val resource2Hash = Text.Resource(R.string.string_with_args).hashCode()
        val pluralsResource1Hash = Text.PluralsResource(R.plurals.plurals_string, 1, 1).hashCode()
        val pluralsResource2Hash = Text.PluralsResource(R.plurals.plurals_string, 2, 2).hashCode()
        val string1Hash = Text.String("text1").hashCode()
        val string2Hash = Text.String("text2").hashCode()

        assertThat(emptyHash).isNotEqualTo(resource1Hash)
        assertThat(emptyHash).isNotEqualTo(pluralsResource1Hash)
        assertThat(emptyHash).isNotEqualTo(string1Hash)

        assertThat(resource1Hash).isNotEqualTo(resource2Hash)
        assertThat(resource1Hash).isNotEqualTo(pluralsResource1Hash)
        assertThat(resource1Hash).isNotEqualTo(string1Hash)

        assertThat(pluralsResource1Hash).isNotEqualTo(pluralsResource2Hash)
        assertThat(pluralsResource1Hash).isNotEqualTo(string1Hash)

        assertThat(string1Hash).isNotEqualTo(string2Hash)
    }

}
