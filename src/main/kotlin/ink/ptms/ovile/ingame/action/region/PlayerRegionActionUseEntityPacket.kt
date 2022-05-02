package ink.ptms.ovile.ingame.action.region

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.player.PlayerRegionActionUseEntityPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
@RegionAction(PlayerRegionActionUseEntityPacket::class)
object PlayerRegionActionUseEntityPacket : PlayerRegionAction<PlayerRegionActionUseEntityPacket> {

    override fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: PlayerRegionActionUseEntityPacket) {
    }
}