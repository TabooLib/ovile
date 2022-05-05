package ink.ptms.ovile.api.block

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Chest

/**
 * Ovile
 * ink.ptms.ovile.api.block.LegacyBlock
 *
 * @author 坏黑
 * @since 2022/5/5 15:13
 */
object LegacyBlock {

    fun getBlockData(block: Block): LegacyBlockData {
        return when (block.state) {
            is Chest -> LegacyChest.wrapper(block.data)
            else -> LegacyBlockData.generic
        }
    }

    fun createBlockData(material: Material): LegacyBlockData {
        return when (material) {
            Material.CHEST, Material.TRAPPED_CHEST -> LegacyChest.wrapper(0)
            else -> LegacyBlockData.generic
        }
    }
}