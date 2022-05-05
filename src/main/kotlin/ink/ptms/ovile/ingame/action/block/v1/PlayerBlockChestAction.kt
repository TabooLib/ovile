package ink.ptms.ovile.ingame.action.block.v1

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.BlockAction
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@BlockAction(BlockAction.Version.BLOCK_STATE)
object PlayerBlockChestAction : BlockStateMatcher() {

    override fun match(item: ItemStack): Boolean {
        return false
    }

    override fun match(block: RegionBlock): Boolean {
        return false
    }

    override fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion) {
//        Bukkit.broadcastMessage("reached 1")
//        val direction = player.getFacingDirection().oppositeFace
//        Bukkit.broadcastMessage("reached 2")
//        Bukkit.broadcastMessage("direction: $direction")
//        val state = chestDirection2IntMapping[direction]!!
//        Bukkit.broadcastMessage("state: $state")
//        region.setBlock(location, RegionBlock.fromBlockState(block.material(), state))
    }
}