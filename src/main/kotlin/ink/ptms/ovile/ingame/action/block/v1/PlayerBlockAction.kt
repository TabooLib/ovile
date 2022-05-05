package ink.ptms.ovile.ingame.action.block.v1

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.BlockAction
import ink.ptms.ovile.ingame.action.block.v1.PlayerBlockChestAction.getFacingDirection
import ink.ptms.ovile.util.extension.isChest
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@BlockAction(BlockAction.Version.BLOCK_STATE)
object PlayerBlockAction : BlockStateMatcher() {

    override fun match(item: ItemStack): Boolean {
        return item.type.isBlock
    }

    override fun match(block: RegionBlock): Boolean {
        return block.material().isBlock
    }

    override fun placeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion, blockFace: BlockFace) {
        if (item.type.isChest) {
            val direction = player.getFacingDirection().oppositeFace
            val state = PlayerBlockChestAction.chestDirection2IntMapping[direction]!!
            region.setBlock(location, RegionBlock.fromBlockState(item.type, state))
            return
        }
        region.setBlock(location, RegionBlock.fromBlockState(item.type, item.durability.toInt()))
    }

    override fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion) {
        region.setBlock(location, RegionBlock.air)
    }
}