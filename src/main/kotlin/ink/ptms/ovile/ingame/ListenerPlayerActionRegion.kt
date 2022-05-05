package ink.ptms.ovile.ingame

import ink.ptms.ovile.*
import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.event.packet.OvilePlayerDigBlockPacket
import ink.ptms.ovile.api.event.packet.OvilePlayerUseEntityPacket
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import ink.ptms.ovile.ingame.action.region.PlayerRegionAction
import ink.ptms.ovile.util.extension.getActiveRegion
import ink.ptms.ovile.util.extension.getRegion
import ink.ptms.ovile.util.extension.sendRegionNotify
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.type.BukkitProxyEvent

/**
 * Ovile
 * ink.ptms.ovile.ingame.ListenerPlayerActionRegion
 *
 * @author 坏黑
 * @since 2022/5/1 23:06
 */
@Suppress("DuplicatedCode", "UNCHECKED_CAST")
object ListenerPlayerActionRegion {

    fun Player.handleEvent(location: Location, region: Region?, active: ActiveRegion?, event: BukkitProxyEvent) {
        if (region == null && active == null) {
            return
        }
        if (region != active?.region) {
            event.isCancelled = true
            sendRegionNotify(location)
        } else {
            getActions(event.javaClass).forEach { it.handle(this, location, region!!, active!!, event) }
        }
    }

    fun Player.handleEvent(location: Location, region: Region?, active: ActiveRegion?, event: Cancellable) {
        if (region == null && active == null) {
            return
        }
        if (region != active?.region) {
            event.isCancelled = true
            sendRegionNotify(location)
        } else {
            getActions(event.javaClass).forEach { it.handle(this, location, region!!, active!!, event) }
        }
    }

    fun getActions(bind: Class<*>): List<PlayerRegionAction<Any>> {
        return OvileAPI.regionActions[bind] as? List<PlayerRegionAction<Any>> ?: emptyList()
    }

    /**
     * 破坏方块（数据包）
     */
    @SubscribeEvent
    fun e(e: OvilePlayerDigBlockPacket) {
        e.player.handleEvent(e.location, e.location.getRegion(), e.player.getActiveRegion(), e)
    }

    /**
     * 使用物品（数据包）
     */
    @SubscribeEvent
    fun e(e: OvilePlayerUseItemPacket) {
        e.player.handleEvent(e.location, e.location.getRegion(), e.player.getActiveRegion(), e)
    }

    /**
     * 交互实体（数据包）
     */
    @SubscribeEvent
    fun e(e: OvilePlayerUseEntityPacket) {
        e.player.handleEvent((e.entity ?: return).location, e.entity!!.location.getRegion() ?: return, e.player.getActiveRegion(), e)
    }

    /**
     * 可能会漏掉的行为（例如：放置载具）
     * 这些数据包通过 PacketPlayInBlockPlace 传递，但是没有坐标参数
     *
     * 暂时无法确定是否还有其他行为通过该数据包
     */
    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        e.player.handleEvent((e.clickedBlock ?: return).location, e.clickedBlock!!.location.getRegion(), e.player.getActiveRegion(), e)
    }

    /**
     * 放置方块（事件）
     */
    @SubscribeEvent
    fun e(e: BlockPlaceEvent) {
        e.player.handleEvent(e.block.location, e.block.location.getRegion(), e.player.getActiveRegion(), e)
    }

    /**
     * 水桶操作（事件）
     */
    @SubscribeEvent
    fun e(e: PlayerBucketFillEvent) {
        e.player.handleEvent(e.block.location, e.block.location.getRegion(), e.player.getActiveRegion(), e)
    }

    @SubscribeEvent
    fun e(e: PlayerBucketEmptyEvent) {
        e.player.handleEvent(e.block.location, e.block.location.getRegion(), e.player.getActiveRegion(), e)
    }
}