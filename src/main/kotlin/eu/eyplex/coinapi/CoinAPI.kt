package eu.eyplex.coinapi

import eu.eyplex.coinapi.commands.CoinsCommand
import eu.eyplex.coinapi.data.StaticDataLoader
import eu.eyplex.coinapi.data.connection.ConnectionProperty
import eu.eyplex.coinapi.data.listener.JoinListener
import eu.eyplex.coinapi.impl.CoinPlayerImpl
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

object CoinAPI {

    private val players = mutableMapOf<UUID, CoinPlayer>()

    fun register(plugin: JavaPlugin, property: ConnectionProperty) {
        StaticDataLoader.loadDatabase(property)
        plugin.server.pluginManager.registerEvents(JoinListener(), plugin)
        plugin.getCommand("coins")?.setExecutor(CoinsCommand())
    }

    fun addPlayer(uuid: UUID) {
        players[uuid] = CoinPlayerImpl(uuid)
    }

    fun getPlayer(uuid: UUID): CoinPlayer {
        return players.getOrDefault(uuid, CoinPlayerImpl(uuid))
    }

}