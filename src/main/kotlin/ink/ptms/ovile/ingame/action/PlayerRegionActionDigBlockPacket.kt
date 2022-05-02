package ink.ptms.ovile.ingame.action

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.event.packet.OvilePlayerDigBlockPacket
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.PlayerRegionActionDigBlockPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
@PlayerAction(OvilePlayerDigBlockPacket::class)
object PlayerRegionActionDigBlockPacket : PlayerRegionAction<OvilePlayerDigBlockPacket> {

    override fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: OvilePlayerDigBlockPacket) {
        event.isCancelled = true
        event.isRefreshBlock = false
        player.sendMessage("破坏方块")
    }
}