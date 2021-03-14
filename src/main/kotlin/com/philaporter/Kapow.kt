package com.philaporter

import com.philaporter.domain.Transaction
import kotlinx.coroutines.*
import java.time.Instant

fun run(x: Transaction) = runBlocking<Unit> {
    GlobalScope.async {
        try {
            work(x)
        } catch (e: TimeoutCancellationException) {
            println(e)
        }
    }
}

suspend fun work(x: Transaction) {
    withTimeout(6000) {
        while (true) {
            println("" + x + " working here! Thread: ${Thread.currentThread().name} Time: " + Instant.now())
            delay(1000)
        }
    }
}