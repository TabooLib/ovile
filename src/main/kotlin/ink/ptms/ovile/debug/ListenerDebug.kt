package ink.ptms.ovile.debug

import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent

object ListenerDebug {

    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
//        val block = e.clickedBlock ?: return
//        Bukkit.broadcastMessage("blockData: ${block.data}")
//        val state = block.state
//        val directionalData = state.data as Directional
//        Bukkit.broadcastMessage("facing: ${directionalData.facing}")
//        Bukkit.broadcastMessage("ordinal: ${directionalData.facing.ordinal}")
    }
}