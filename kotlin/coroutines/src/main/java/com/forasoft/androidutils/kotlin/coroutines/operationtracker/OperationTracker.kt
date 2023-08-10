package com.forasoft.androidutils.kotlin.coroutines.operationtracker

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.*

/**
 * Tracks ongoing operations started with [track] and uses [OperationKey] to distinguish
 * between them.
 *
 * Use [isOperationOngoing] and [ongoingOperationKeys] to check if the operation is
 * ongoing or not.
 */
public class OperationTracker {

    private val ongoingOperations = MutableStateFlow(setOf<Operation>())

    /**
     * [Flow] that contains [Set] of [OperationKey]s of the ongoing operations.
     */
    public val ongoingOperationKeys: Flow<Set<OperationKey>> = ongoingOperations
        .map { operations ->
            operations.map { request -> request.key }.toSet()
        }

    /**
     * Runs [operation], tracks its status and returns the result. [key] used to
     * distinguish the given operation from others.
     *
     * @param key [OperationKey] used to distinguish the given operation.
     * @param operation operation to track.
     * @return result of the [operation].
     */
    public suspend fun <R : Any> track(key: OperationKey, operation: suspend () -> R): R {
        val request = Operation(UUID.randomUUID().toString(), key)
        try {
            ongoingOperations.update { it + request }
            return operation()
        } finally {
            ongoingOperations.update { it - request }
        }
    }

    /**
     * Returns [Flow] that indicates if there is **at least one** ongoing operation with
     * a key from the given [keys].
     */
    public fun isOperationOngoing(vararg keys: OperationKey): Flow<Boolean> {
        val keySet = keys.toSet()
        return ongoingOperationKeys.map { ongoingKeys ->
            ongoingKeys.any { it in keySet }
        }
    }

    public companion object

}
