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

    // 在 Paper 1.12.2 服务端环境下测试的结果, 其他子版本 & 低版本未确认是否一致
    internal val chestDirection2IntMapping by lazy {
        HashMap<BlockFace, Int>().also {
            it += BlockFace.SOUTH to 3
            it += BlockFace.EAST to 5
            it += BlockFace.NORTH to 2
            it += BlockFace.WEST to 4
        }
    }

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

    // 在服务端 1.12.2 环境下没有获取方向方法
    // 及箱子方块的方向不能有偏移 (必须为东南西北), 否则无法获取相关状态映射
    internal fun Player.getFacingDirection(): BlockFace {
        var yaw = location.yaw
        if (yaw < 0.0) yaw += 360.0f
        if (yaw >= 315 || yaw < 45) {
            return BlockFace.SOUTH
        } else if (yaw < 135) {
            return BlockFace.WEST
        } else if (yaw < 225) {
            return BlockFace.NORTH
        } else if (yaw < 315) {
            return BlockFace.EAST
        }
        return BlockFace.NORTH
    }
}