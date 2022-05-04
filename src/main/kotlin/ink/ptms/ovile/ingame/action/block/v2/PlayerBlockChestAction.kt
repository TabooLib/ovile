package ink.ptms.ovile.ingame.action.block.v2

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.BlockAction
import ink.ptms.ovile.ingame.nms.BlockDataImpl
import ink.ptms.ovile.left
import ink.ptms.ovile.relative
import ink.ptms.ovile.right
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.Chest
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.block.v2.PlayerBlockChestAction
 *
 * @author 坏黑
 * @since 2022/5/2 13:21
 */
@BlockAction(BlockAction.Version.BLOCK_DATA)
object PlayerBlockChestAction : BlockDataMatcher() {

    override fun match(block: RegionBlock): Boolean {
        return block.getBlockData() is Chest
    }

    override fun match(item: ItemStack): Boolean {
        return item.getBlockData() is Chest
    }

    override fun placeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion, blockFace: BlockFace) {
        region.setBlock(location, RegionBlock.fromBlockData(item.type.createBlockData { data ->
            data as Chest
            data.facing = player.facing.oppositeFace
            // 下蹲不合并
            if (player.isSneaking) {
                return@createBlockData
            }
            // 处理合并
            val right = location.relative(data.facing.right())
            val rightBlock = region.getBlock<BlockDataImpl>(right)
            val rightData = rightBlock?.data
            if (rightData is Chest && rightData.material == data.material && rightData.type == Chest.Type.SINGLE && rightData.facing == data.facing) {
                data.type = Chest.Type.LEFT
                rightData.type = Chest.Type.RIGHT
                region.players.forEach { rightBlock.sendTo(it, right) }
            } else {
                val left = location.relative(data.facing.left())
                val leftBlock = region.getBlock<BlockDataImpl>(left)
                val leftData = leftBlock?.data
                if (leftData is Chest && leftData.material == data.material && leftData.type == Chest.Type.SINGLE && leftData.facing == data.facing) {
                    data.type = Chest.Type.RIGHT
                    leftData.type = Chest.Type.LEFT
                    region.players.forEach { leftBlock.sendTo(it, left) }
                }
            }
        }))
    }

    override fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion) {
        region.setBlock(location, RegionBlock.air)
        // 拆分箱子
        val data = block.getBlockData() as Chest
        val right = location.relative(data.facing.right())
        val rightBlock = region.getBlock<BlockDataImpl>(right)
        val rightData = rightBlock?.data
        if (rightData is Chest && rightData.material == data.material && rightData.type == Chest.Type.RIGHT && rightData.facing == data.facing) {
            rightData.type = Chest.Type.SINGLE
            region.players.forEach { rightBlock.sendTo(it, right) }
        } else {
            val left = location.relative(data.facing.left())
            val leftBlock = region.getBlock<BlockDataImpl>(left)
            val leftData = leftBlock?.data
            if (leftData is Chest && leftData.material == data.material && leftData.type == Chest.Type.LEFT && leftData.facing == data.facing) {
                leftData.type = Chest.Type.SINGLE
                region.players.forEach { leftBlock.sendTo(it, left) }
            }
        }
    }
}