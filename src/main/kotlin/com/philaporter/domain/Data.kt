package com.philaporter.domain

data class Transaction(val id: String, val amount: Double)

class Account(val accountId: String, var totalSpend: Double) {

    fun increaseTotalSpend(a : Double) {
        totalSpend += a;
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