package com.philaporter.domain

import java.util.concurrent.atomic.AtomicBoolean

data class Transaction(val id: String, val accountId: String, val amount: Double)

class Account(val accountId: String, var totalSpend: Double, var locked: AtomicBoolean) {

    fun increaseTotalSpend(a: Double) {
        totalSpend += a;
    }

    fun lock(): Boolean {
        return locked.compareAndSet(false, true)
    }

    fun unlock(): Boolean {
        return locked.compareAndSet(true, false)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (accountId != other.accountId) return false
        if (totalSpend != other.totalSpend) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accountId.hashCode()
        result = 31 * result + totalSpend.hashCode()
        return result
    }

    override fun toString(): String {
        return "Account(accountId='$accountId', totalSpend=$totalSpend)"
    }
}