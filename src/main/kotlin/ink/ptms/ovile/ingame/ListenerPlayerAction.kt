package ink.ptms.ovile.ingame

import ink.ptms.ovile.OvileAPI.ignoreBlockChangeMap
import ink.ptms.ovile.OvileAPI.notifyBaffle
import ink.ptms.ovile.util.extension.getRegion
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

/**
 * Ovile
 * ink.ptms.ovile.ingame.ListenerPlayerAction
 *
 * @author 坏黑
 * @since 2022/5/1 20:39
 */
object ListenerPlayerAction {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        notifyBaffle.reset(e.player.name)
        ignoreBlockChangeMap.remove(e.player.name)
    }

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun e(e: PlayerMoveEvent) {
        checkPlayerRegion(e.player, e.from, e.to ?: return)
    }

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun e(e: PlayerTeleportEvent) {
        checkPlayerRegion(e.player, e.from, e.to ?: return)
    }

    fun checkPlayerRegion(player: Player, from: Location, to: Location) {
        if (from.toVector() == to.toVector()) {
            return
        }
        val regionFrom = from.getRegion()
        val regionTo = to.getRegion()
        if (regionFrom != regionTo) {
            regionFrom?.leave(player)
            regionTo?.enter(player)
        }
    }
}