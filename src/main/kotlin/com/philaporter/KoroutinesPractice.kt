@file:JvmName("Runner")

package com.philaporter

import com.philaporter.domain.Transaction
import kotlinx.coroutines.*
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeoutException
import kotlin.random.Random

fun runAsync(x: Transaction): CompletableFuture<Boolean> {
    var future = CompletableFuture<Boolean>()
    CoroutineScope(Dispatchers.IO).async {
        try {
            work(x)
            future.complete(true)
        } catch (e: TimeoutCancellationException) {
            future.completeExceptionally(e)
        }
    }
    return future
}

suspend fun work(x: Transaction) = withContext(Dispatchers.IO) {
    withTimeout(3000) {

        while (true) {
            if (Random.nextInt(25) == x.id.toInt()) {
                println("${coroutineContext[Job]} for $x is working in ${Thread.currentThread().name} in " +
                        "${Thread.currentThread().threadGroup} at " + Instant.now())
                break;
            } else {
                println("${coroutineContext[Job]} for $x is waiting")
                delay(100)
            }
        }
    }
}