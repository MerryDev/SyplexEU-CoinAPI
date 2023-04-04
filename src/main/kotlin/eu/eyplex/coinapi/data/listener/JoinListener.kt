package eu.eyplex.coinapi.data.listener

import eu.eyplex.coinapi.CoinAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        CoinAPI.addPlayer(event.player.uniqueId)
        CoinAPI.getPlayer(event.player.uniqueId).registerDefault()
    }

}