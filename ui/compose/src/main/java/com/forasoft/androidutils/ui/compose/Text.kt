@file:Suppress("Unused")

package com.forasoft.androidutils.ui.compose

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

// TODO: Issue. Find a way to combine with ui.view.Text to avoid duplicating?

/**
 * Interface that allows to present text in different forms.
 */
@Stable
sealed interface Text {

    /**
     * Returns a [String] value of the text.
     */
    fun getString(context: Context): kotlin.String

    /**
     * Represents an empty text with a value of empty [String].
     */
    object Empty : Text {
        override fun getString(context: Context): kotlin.String = ""
    }

    /**
     * Represents a resource text.
     *
     * @property resourceId id of the resource string.
     * @property args parameters for the parametrized string.
     */
    class Resource(
        @StringRes val resourceId: Int,
        private vararg val args: Any = emptyArray(),
    ) : Text {

        override fun getString(context: Context): kotlin.String {
            @Suppress("SpreadOperator")
            return context.resources.getString(this.resourceId, *this.args)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Resource) return false

            if (resourceId != other.resourceId) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resourceId
            result = 31 * result + args.contentHashCode()
            return result
        }

    }

    /**
     * Represents a plurals resource text.
     *
     * @property resourceId id of the plurals resource string.
     * @property count the number used to get the correct string for the plural rules.
     * @property args parameters for the parametrized string.
     */
    class PluralsResource(
        @PluralsRes val resourceId: Int,
        private val count: Int,
        private vararg val args: Any = emptyArray(),
    ) : Text {

        override fun getString(context: Context): kotlin.String {
            @Suppress("SpreadOperator")
            return context.resources.getQuantityString(this.resourceId, this.count, *this.args)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is PluralsResource) return false

            if (resourceId != other.resourceId) return false
            if (count != other.count) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resourceId
            result = 31 * result + count
            result = 31 * result + args.contentHashCode()
            return result
        }

    }

    /**
     * Represents the plain [String] text.
     */
    class String(private val text: kotlin.String) : Text {

        override fun getString(context: Context): kotlin.String = text

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is String) return false

            if (text != other.text) return false

            return true
        }

        override fun hashCode(): Int = text.hashCode()

    }

}
