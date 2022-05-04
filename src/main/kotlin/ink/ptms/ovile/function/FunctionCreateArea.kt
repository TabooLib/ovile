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

    val selectPoints = ArrayList<Location>().apply {
        repeat(2) {
            add(emptyLocation)
        }
    }

    fun isLegalLocation() = !selectPoints.contains(emptyLocation)

    // 当创造模式下, 手持线左右键进行选点操作
    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        val player = e.player
        val clickedBlock = e.clickedBlock ?: return
        if (!player.isInCreateMode()) {
            return
        }
        e.isCancelled = true
        when (e.action) {
            Action.LEFT_CLICK_BLOCK -> {
                selectPoints[0] = clickedBlock.location
                player.sendLang("player-a-point-select")
            }
            Action.RIGHT_CLICK_BLOCK -> {
                selectPoints[1] = clickedBlock.location
                player.sendLang("player-b-point-select")
            }
            else -> return
        }
    }

    private fun Player.isInCreateMode() = gameMode == GameMode.CREATIVE && inventory.itemInMainHand.type == Material.STRING
}