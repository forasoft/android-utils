package com.forasoft.androidutils.kotlin.coroutines.flow

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class StateFlowKtTest {

    @Test
    fun mapState_returnsTransformedStateFlow() = runTest {
        val original = MutableStateFlow(8)
        val transform = { value: Int -> value * 31 }

        val transformScope = CoroutineScope(StandardTestDispatcher())
        val transformed = original.mapState(transformScope, transform = transform)

        assertThat(transformed).isInstanceOf(StateFlow::class.java)
        assertThat(transformed.value).isEqualTo(transform(original.value))
    }

}
