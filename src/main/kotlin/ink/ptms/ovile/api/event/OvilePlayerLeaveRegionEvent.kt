package ink.ptms.ovile.api.event

import ink.ptms.ovile.api.ActiveRegion
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Ovile
 * ink.ptms.ovile.api.event.OvilePlayerLeaveRegionEvent
 *
 * @author 坏黑
 * @since 2022/5/1 20:48
 */
class OvilePlayerLeaveRegionEvent(val player: Player, val region: ActiveRegion): BukkitProxyEvent() {

    override val allowCancelled: Boolean
        get() = false
}