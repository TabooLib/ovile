package ink.ptms.ovile.api.event

import ink.ptms.ovile.api.ActiveRegion
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Ovile
 * ink.ptms.ovile.api.event.OvilePlayerEnterRegionEvent
 *
 * @author 坏黑
 * @since 2022/5/1 20:42
 */
class OvilePlayerEnterRegionEvent(val player: Player, var region: ActiveRegion) : BukkitProxyEvent()