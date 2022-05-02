package ink.ptms.ovile.ingame.action.block

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.RegionBlock
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
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

    /**
     * 通过物品判断类型
     */
    fun match(item: ItemStack): Boolean

    /**
     * 通过方块判断类型
     */
    fun match(block: RegionBlock): Boolean

    /**
     * 破坏方块
     */
    fun breakBlock(player: Player, block: RegionBlock, location: Location, region: ActiveRegion) {
    }

    /**
     * 放置方块
     */
    fun placeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion, blockFace: BlockFace) {
    }

    /**
     * 合并方块（例如：半砖）
     */
    fun mergeBlock(player: Player, item: ItemStack, location: Location, region: ActiveRegion, blockFace: BlockFace, original: RegionBlock): Boolean {
        return false
    }
}