package com.forasoft.androidutils.kotlin.coroutines.operationtracker

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.*

class OperationTracker {

    private val ongoingOperations = MutableStateFlow(setOf<Operation>())
    val ongoingOperationKeys = ongoingOperations
        .map {
            it.map { request -> request.key }.toSet()
        }

    suspend fun <R : Any> track(key: OperationKey, body: suspend () -> R): R {
        val request = Operation(UUID.randomUUID().toString(), key)
        try {
            ongoingOperations.update { it + request }
            return body()
        } finally {
            ongoingOperations.update { it - request }
        }
    }

    fun isOperationOngoing(vararg keys: OperationKey): Flow<Boolean> {
        val keySet = keys.toSet()
        return ongoingOperationKeys.map { ongoingKeys ->
            ongoingKeys.any { it in keySet }
        }
    }

}
