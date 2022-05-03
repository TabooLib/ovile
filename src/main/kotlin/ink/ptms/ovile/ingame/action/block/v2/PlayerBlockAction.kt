package ink.ptms.ovile.ingame.action.block.v2

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.BlockAction
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.block.v2.PlayerBlockAction
 *
 * @author 坏黑
 * @since 2022/5/2 13:21
 */
@BlockAction(BlockAction.Version.BLOCK_DATA)
object PlayerBlockAction : BlockDataMatcher() {

    override fun match(item: ItemStack): Boolean {
        return item.getBlockData().javaClass.simpleName == "CraftBlockData"
    }

    override fun match(block: RegionBlock): Boolean {
        return block.getBlockData().javaClass.simpleName == "CraftBlockData"
    }

    override fun placeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion, blockFace: BlockFace) {
        region.setBlock(location, RegionBlock.of(item.type.createBlockData()))
    }

    override fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion) {
        region.setBlock(location, RegionBlock.air)
    }
}