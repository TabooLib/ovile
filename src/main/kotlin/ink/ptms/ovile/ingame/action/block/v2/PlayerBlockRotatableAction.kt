package ink.ptms.ovile.ingame.action.block.v2

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.BlockAction
import org.bukkit.Axis
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Orientable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.block.v2.PlayerBlockRotatableAction
 *
 * @author 坏黑
 * @since 2022/5/2 13:21
 */
@BlockAction(BlockAction.Version.BLOCK_DATA)
object PlayerBlockRotatableAction : BlockDataMatcher() {

    override fun match(item: ItemStack): Boolean {
        return item.getBlockData().javaClass.simpleName == "CraftRotatable"
    }

    override fun match(block: RegionBlock): Boolean {
        return block.getBlockData().javaClass.simpleName == "CraftRotatable"
    }

    override fun placeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion, blockFace: BlockFace) {
        region.setBlock(location, RegionBlock.fromBlockData(item.type.createBlockData { data ->
            data as Orientable
            data.axis = when (blockFace) {
                BlockFace.EAST, BlockFace.WEST -> Axis.X
                BlockFace.NORTH, BlockFace.SOUTH -> Axis.Z
                else -> Axis.Y
            }
        }))
    }

    override fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion) {
        region.setBlock(location, RegionBlock.air)
    }
}