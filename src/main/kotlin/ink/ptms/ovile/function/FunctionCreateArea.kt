package ink.ptms.ovile.function

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

object FunctionCreateArea {

    private val emptyLocation: Location
        get() = Bukkit.getWorlds()[0]!!.spawnLocation

    val selectPoints = ArrayList<Location>().apply { for (i in 1..2) add(emptyLocation) }

    fun isLegalLocation(): Boolean = !selectPoints.contains(emptyLocation)

    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        val player = e.player
        val clickedBlock = e.clickedBlock ?: return
        if (player.isInCreateMode() && e.action != Action.PHYSICAL) {
            e.isCancelled = true
            if (e.action == Action.LEFT_CLICK_BLOCK || e.action == Action.LEFT_CLICK_AIR) {
                selectPoints[0] = clickedBlock.location
                player.sendLang("player-a-point-select")
                return
            }
            selectPoints[1] = clickedBlock.location
            player.sendLang("player-b-point-select")
        }
    }

    private fun Player.isInCreateMode() = gameMode == GameMode.CREATIVE && inventory.itemInMainHand.type == Material.STRING
}