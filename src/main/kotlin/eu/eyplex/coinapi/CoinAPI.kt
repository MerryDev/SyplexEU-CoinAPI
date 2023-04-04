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

    /**
     * Registers this API into a plugin.
     * Also, the additional command and the required listener are registered.
     *
     * @param[plugin] The plugin main class
     * @param[property] A [ConnectionProperty] containing credentials for a successful database connection
     */
    fun register(plugin: JavaPlugin, property: ConnectionProperty) {
        StaticDataLoader.loadDatabase(property)
        plugin.server.pluginManager.registerEvents(JoinListener(), plugin)
        plugin.getCommand("coins")?.setExecutor(CoinsCommand())
    }

    /**
     * Gets a CoinPlayer from the cache.
     *
     * @param[uuid] The uuid of the player
     * @return An existing CoinPlayer containing existent data or a new instance having default values.
     */
    fun getPlayer(uuid: UUID): CoinPlayer {
        return players.getOrDefault(uuid, CoinPlayerImpl(uuid))
    }

    /**
     * Caches a player joining the server.
     * Important: Do not under any circumstances use this function in your plugin. It is for the backend of this API only.
     *
     * @param[uuid] The uuid of the player
     */
    fun addPlayer(uuid: UUID) {
        players[uuid] = CoinPlayerImpl(uuid)
    }

}