package ink.ptms.ovile.ingame.action.block.v2

import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.PlayerBlockAction
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ConcurrentHashMap

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.block.v2.BlockDataMatcher
 *
 * @author 坏黑
 * @since 2022/5/2 13:28
 */
abstract class BlockDataMatcher : PlayerBlockAction {

    fun ItemStack.getBlockData(): BlockData {
        return blockDataCacheMap.computeIfAbsent(type) { type.createBlockData() }
    }

    fun RegionBlock.getBlockData(): BlockData {
        return (this as RegionBlock.BlockDataImpl).data
    }

    companion object {

        internal val blockDataCacheMap = ConcurrentHashMap<Material, BlockData>()
    }
}