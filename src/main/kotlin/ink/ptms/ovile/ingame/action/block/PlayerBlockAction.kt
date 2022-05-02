package ink.ptms.ovile.ingame.action.block

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Ovile
 * ink.ptms.ovile.ingame.block.PlayerBlockAction
 *
 * @author 坏黑
 * @since 2022/5/2 13:03
 */
interface PlayerBlockAction {

    fun match(block: RegionBlock): Boolean

    fun match(item: ItemStack): Boolean

    fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion)

    fun placeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion)
}