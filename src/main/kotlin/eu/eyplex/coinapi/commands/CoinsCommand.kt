package eu.eyplex.coinapi.commands

import eu.eyplex.coinapi.CoinAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CoinsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (sender !is Player) {
            sender.sendMessage(Component.text("Dazu musst du ein Spieler sein!").color(NamedTextColor.RED))
            return true
        }

        val player: Player = sender

        if (args == null) return true // Something went wrong while performing the command logic

        if (args.isEmpty()) { // No arguments were passed, so the player's amount of coins is send
            sendAmountMessage(player)

        } else {
            if (!player.hasPermission("coinapi.command.coins")) {
                sender.sendMessage(Component.text("Dazu hast du leider keine Rechte!").color(NamedTextColor.RED))
                return true
            }

            val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(args[0])

            if (args.size == 1) { // Only a name was passed as an argument, so the coins of that player are send
                sendAmountMessage(player, offlinePlayer)

            } else if (args.size == 3) { // command logic for modifying coins
                val playerName = offlinePlayer.name

                if (playerName == null) {
                    player.sendMessage(Component.text("Dieser Spieler hat noch nie auf diesem Server gespielt!"))
                    return true
                }

                val coins = assertAmountIsNumber(player, args[2])

                if (args[1].equals("set", true)) { // Set player coins to a specific value
                    CoinAPI.getPlayer(offlinePlayer.uniqueId).setCoins(coins)

                    val message = Component.text("Du hast die Coins von ").color(NamedTextColor.GRAY)
                        .append(Component.text(playerName).color(NamedTextColor.GOLD))
                        .append(Component.text(" auf die Anzahl ").color(NamedTextColor.GRAY))
                        .append(Component.text(coins).color(NamedTextColor.AQUA))
                        .append(Component.text(" gesetzt.").color(NamedTextColor.GRAY))

                    player.sendMessage(message)

                } else if (args[1].equals("add", true)) { // Adds a specific value to existing coins
                    CoinAPI.getPlayer(offlinePlayer.uniqueId).addCoins(coins)

                    val message = Component.text("Du hast dem Spieler ").color(NamedTextColor.GRAY)
                        .append(Component.text(playerName).color(NamedTextColor.GOLD))
                        .append(Component.text("$coins Coins ").color(NamedTextColor.AQUA))
                        .append(Component.text("hinzugefügt.").color(NamedTextColor.GRAY))

                    player.sendMessage(message)

                } else if (args[1].equals("remove", true)) { // Remove a specific value from existing coins
                    CoinAPI.getPlayer(offlinePlayer.uniqueId).removeCoins(coins)

                    val message = Component.text("Du hast dem Spieler ").color(NamedTextColor.GRAY)
                        .append(Component.text(playerName).color(NamedTextColor.GOLD))
                        .append(Component.text("$coins Coins ").color(NamedTextColor.AQUA))
                        .append(Component.text("entfernt.").color(NamedTextColor.GRAY))

                    player.sendMessage(message)

                } else {
                    player.sendMessage(Component.text("Bitte benutze /coins <Name> <set/add/remove> <Anzahl>!"))
                }


            } else { // Everything went wrong
                player.sendMessage(Component.text("Bitte benutze /coins <Name> <set/add/remove> <Anzahl>!"))
            }
        }

        return true
    }

    private fun assertAmountIsNumber(player: Player, raw: String): Int {
        return try {
            raw.toInt()

        } catch (exception: NumberFormatException) {
            player.sendMessage(Component.text("Die Anzahl muss eine Zahl sein!").color(NamedTextColor.RED))
            -1
        }
    }

    private fun sendAmountMessage(player: Player, offlinePlayer: OfflinePlayer) {
        val message = Component.text("Du hast derzeit ").color(NamedTextColor.GRAY)
            .append(Component.text(CoinAPI.getPlayer(offlinePlayer.uniqueId).getCoins()).color(NamedTextColor.GOLD))
            .append(Component.text(" Coins!").color(NamedTextColor.GRAY))

        player.sendMessage(message)
    }

    private fun sendAmountMessage(player: Player) {
        val message = Component.text("Du hast derzeit ").color(NamedTextColor.GRAY)
            .append(Component.text(CoinAPI.getPlayer(player.uniqueId).getCoins()).color(NamedTextColor.GOLD))
            .append(Component.text(" Coins!").color(NamedTextColor.GRAY))

        player.sendMessage(message)
    }
}