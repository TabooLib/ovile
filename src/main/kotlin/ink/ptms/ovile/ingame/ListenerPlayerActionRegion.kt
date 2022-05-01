package ink.ptms.ovile.ingame

import ink.ptms.ovile.api.event.packet.OvilePlayerDigBlockPacket
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import ink.ptms.ovile.getActiveRegion
import ink.ptms.ovile.getRegion
import taboolib.common.platform.event.SubscribeEvent

/**
 * Ovile
 * ink.ptms.ovile.ingame.ListenerPlayerActionRegion
 *
 * @author 坏黑
 * @since 2022/5/1 23:06
 */
object ListenerPlayerActionRegion {

    @SubscribeEvent
    fun e(e: OvilePlayerUseItemPacket) {
        val region = e.location.getRegion()
        val activeRegion = e.player.getActiveRegion()
        if (region == null && activeRegion == null) {
            return
        }
        // 玩家所在区域与交互方块所在区域不同
        if (region != activeRegion?.region) {
            e.isCancelled = true
            e.player.sendMessage("你不能跨区域交互")
        }
    }

    @SubscribeEvent
    fun e(e: OvilePlayerDigBlockPacket) {
    }
}