package ink.ptms.ovile.api

import ink.ptms.ovile.ingame.nms.BlockDataImpl
import ink.ptms.ovile.ingame.nms.BlockStateImpl
import ink.ptms.ovile.util.Version
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player

/**
 * Ovile
 * ink.ptms.ovile.api.RegionBlock
 *
 * @author 坏黑
 * @since 2022/5/2 12:33
 */
interface RegionBlock {

    val blockActionType: String

    fun material(): Material

    fun sendTo(player: Player, location: Location)

    companion object {

        val air by lazy {
            if (Version.isLegacyVersion) {
                BlockStateImpl(Material.AIR, 0)
            } else {
                BlockDataImpl(Material.AIR.createBlockData())
            }
        }

        fun fromBlockData(data: Any): RegionBlock {
            return BlockDataImpl(data as BlockData)
        }

        fun fromBlockState(material: Material, data: Int): RegionBlock {
            return BlockStateImpl(material, data)
        }

        fun fromBlock(block: Block): RegionBlock {
            return if (Version.isLegacyVersion) {
                BlockStateImpl(block.type, block.data.toInt())
            } else {
                BlockDataImpl(block.blockData)
            }
        }
    }
}