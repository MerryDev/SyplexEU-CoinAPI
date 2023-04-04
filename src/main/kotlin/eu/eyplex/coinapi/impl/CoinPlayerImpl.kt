package eu.eyplex.coinapi.impl

import eu.eyplex.coinapi.CoinPlayer
import eu.eyplex.coinapi.data.sadu.StaticQueryAdapter
import java.util.*
import java.util.concurrent.CompletableFuture

class CoinPlayerImpl(private val uuid: UUID) : CoinPlayer {

    private var coins: Int = 0

    init {
        getCoinsAsync().whenComplete { coins, _ -> this.coins = coins!!.orElse(-1) }
    }

    override fun getCoinsSync(): Optional<Int> {
        return StaticQueryAdapter.builder(Int::class.java)
            .query("SELECT  coins FROM player_coins WHERE uuid=?;")
            .parameter { stmt -> stmt.setString(uuid.toString()) }
            .readRow { row -> row.getInt("coins") }
            .firstSync()
    }

    override fun getCoinsAsync(): CompletableFuture<Optional<Int>> {
        return StaticQueryAdapter.builder(Int::class.java)
            .query("SELECT coins FROM player_coins WHERE uuid=?;")
            .parameter { stmt -> stmt.setString(uuid.toString()) }
            .readRow { row -> row.getInt("coins") }
            .first()
    }

    override fun setCoinsAsync(amount: Int) {
        StaticQueryAdapter.builder()
            .query("INSERT INTO player_coins (uuid, coins) VALUES (? , ?) ON DUPLICATE KEY UPDATE coins=?;")
            .parameter { stmt ->
                stmt.setString(uuid.toString())
                stmt.setInt(0)
                stmt.setInt(amount)
            }
            .update()
            .send()
            .whenComplete { _, _ -> coins = amount }
    }

    override fun getCoins(): Int {
        return coins
    }

    override fun addCoins(amount: Int) {
        setCoins(coins + amount)
    }

    override fun removeCoins(amount: Int) {
        setCoinsAsync((coins - amount).coerceAtLeast(0))
    }

    override fun setCoins(amount: Int) {
        setCoinsAsync(amount)
    }

    override fun registerDefault() {
        if (coins == -1) setCoins(0)
    }
}