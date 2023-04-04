package eu.eyplex.coinapi

import java.util.*
import java.util.concurrent.CompletableFuture

interface CoinPlayer {

    fun getCoins(): Int

    fun getCoinsSync(): Optional<Int>

    fun getCoinsAsync(): CompletableFuture<Optional<Int>>

    fun setCoinsAsync(amount: Int)

    fun addCoins(amount: Int)

    fun removeCoins(amount: Int)

    fun setCoins(amount: Int)

    fun registerDefault()

}