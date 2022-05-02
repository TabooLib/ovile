package ink.ptms.ovile.ingame.action.region

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.BlockPlaceEvent
 *
 * @author 坏黑
 * @since 2022/5/2 10:18
 */
@RegionAction(BlockPlaceEvent::class)
object PlayerRegionActionBlockPlaceEvent : PlayerRegionAction<BlockPlaceEvent> {

    override fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: BlockPlaceEvent) {
//        event.isCancelled = true
//        player.ignoreBlockChange(event.blockPlaced.location)
//        player.sendMessage(event.blockPlaced.blockData.asString)
//        active.setBlock(location, event.blockPlaced.blockData, listOf(player))
    }
}