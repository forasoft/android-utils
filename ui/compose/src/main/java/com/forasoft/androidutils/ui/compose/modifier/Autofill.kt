package com.forasoft.androidutils.ui.compose.modifier

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree

// Courtesy of https://bryanherbst.com/2021/04/13/compose-autofill/

/**
 * Mark Composable as an autofill receiver
 *
 * @param autofillTypes autofill type markers
 * @param onFill callback triggered when the autocomplete is performed. The parameter of the
 * lambda is the value that has been provided by autofill
 */
@OptIn(ExperimentalComposeUiApi::class)
public fun Modifier.autofill(
    autofillTypes: List<AutofillType>,
    onFill: (autofilledValue: String) -> Unit,
): Modifier = composed {
    val autofill = LocalAutofill.current
    val autofillNode = AutofillNode(onFill = onFill, autofillTypes = autofillTypes)
    LocalAutofillTree.current += autofillNode

    this
        .onGloballyPositioned {
            autofillNode.boundingBox = it.boundsInWindow()
        }
        .onFocusChanged { focusState ->
            autofill?.run {
                if (focusState.isFocused) {
                    requestAutofillForNode(autofillNode)
                } else {
                    cancelAutofillForNode(autofillNode)
                }
            }
        }
}
