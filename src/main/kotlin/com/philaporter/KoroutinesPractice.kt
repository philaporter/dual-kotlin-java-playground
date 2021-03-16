@file:JvmName("Runner")

package com.philaporter

import com.philaporter.domain.Account
import com.philaporter.domain.Transaction
import kotlinx.coroutines.*
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

fun runAsync(x: Transaction, chm: ConcurrentHashMap<String, Account>): CompletableFuture<Boolean> {
    var future = CompletableFuture<Boolean>()
    CoroutineScope(Dispatchers.IO).async {
        try {
            work(x, chm)
            future.complete(true)
        } catch (e: TimeoutCancellationException) {
            future.completeExceptionally(e)
        }
    }
    return future
}

suspend fun work(t: Transaction, chm: ConcurrentHashMap<String, Account>) = withContext(Dispatchers.IO) {
    withTimeout(6000) {

        var account: Account
        while (true) {

            account = chm.get(t.accountId)!!
            if (account.lock()) {
                println(
                    "${coroutineContext[Job]} for $t is working in ${Thread.currentThread().name} in " +
                            "${Thread.currentThread().threadGroup} at ${Instant.now()}"
                )
                account.increaseTotalSpend(t.amount)
                account.unlock()
                break;
            } else {
                println("${coroutineContext[Job]} for $t is waiting")
                delay(100)
            }
        }
    }
}