package ink.ptms.ovile.ingame.action.region

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.api.event.packet.OvilePlayerDigBlockPacket
import ink.ptms.ovile.blockActionType
import ink.ptms.ovile.matchBlockAction
import ink.ptms.ovile.sendRegionNotify
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.player.PlayerRegionActionDigBlockPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
@RegionAction(OvilePlayerDigBlockPacket::class)
object PlayerRegionActionDigBlockPacket : PlayerRegionAction<OvilePlayerDigBlockPacket> {

    override fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: OvilePlayerDigBlockPacket) {
        event.isCancelled = true
        event.region = active
        val regionBlock = active.getBlock(location) ?: RegionBlock.of(location.block)
        val matchBlockAction = regionBlock.matchBlockAction()
        if (matchBlockAction == null) {
            player.sendRegionNotify(location, event.direction.toBukkit(), "player-action-no-implementation", regionBlock.blockActionType())
            return
        }
        matchBlockAction.breakBlock(player, regionBlock, location, active)
        player.sendMessage("破坏方块")
    }
}