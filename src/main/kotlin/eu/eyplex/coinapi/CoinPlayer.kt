package eu.eyplex.coinapi

import java.util.*
import java.util.concurrent.CompletableFuture

interface CoinPlayer {

    /**
     * Gets the cached amount of coins.
     */
    fun getCoins(): Int

    /**
     * Queries the current amount of coins directly from the database using a synchonous call.
     */
    fun getCoinsSync(): Optional<Int>

    /**
     * Queries the current amount of coins directly from the database using an asynchronous call.
     * This function is used to initialize a CoinPlayer.
     *
     * @return A CompletableFuture holding an Optional. Last contains the amount of coins or the default value -1 if something went wrong.
     */
    fun getCoinsAsync(): CompletableFuture<Optional<Int>>

    /**
     * Updates the amount of coins in the database to the given value using an asynchronous call.
     *
     * @param[amount] The new value stored value
     */
    fun setCoinsAsync(amount: Int)

    /**
     * Adds the amount to the current value stored in the database.
     *
     * @param[amount] The value to add
     * @see[setCoinsAsync]
     */
    fun addCoins(amount: Int)

    /**
     * Removes the amount from the current stored value. Note that the new value cannot be lower than 0.
     *
     * @param[amount] The value to remove
     * @see[setCoinsAsync]
     */
    fun removeCoins(amount: Int)

    /**
     * Updates the stored amount in the database to the given value.
     * This function does exactly the same as [setCoinsAsync]
     *
     * @param[amount] The new stored value
     * @see[setCoinsAsync]
     */
    fun setCoins(amount: Int)

    /**
     * Updates the amount of coins from a recently new created CoinPlayer to 0, because the default return value -1 represents an error in the process of calling the database.
     *
     * @see[setCoins]
     */
    fun registerDefault()

}