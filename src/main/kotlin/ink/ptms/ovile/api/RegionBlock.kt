package ink.ptms.ovile.api

import ink.ptms.ovile.ingame.nms.BlockDataImpl
import ink.ptms.ovile.ingame.nms.BlockStateImpl
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import taboolib.module.nms.MinecraftVersion

/**
 * Ovile
 * ink.ptms.ovile.api.RegionBlock
 *
 * @author 坏黑
 * @since 2022/5/2 12:33
 */
interface RegionBlock {

    fun material(): Material

    fun sendTo(player: Player, location: Location)

    companion object {

        val air = if (MinecraftVersion.majorLegacy > 11200) {
            BlockDataImpl(Material.AIR.createBlockData())
        } else {
            BlockStateImpl(Material.AIR, 0)
        }

        fun fromBlockData(data: Any): RegionBlock {
            return BlockDataImpl(data as BlockData)
        }
        
        fun fromBlockState(material: Material, data: Int): RegionBlock {
            return BlockStateImpl(material, data)
        }

        fun of(block: Block): RegionBlock {
            return if (MinecraftVersion.majorLegacy > 11200) {
                BlockDataImpl(block.blockData)
            } else {
                BlockStateImpl(block.type, block.data.toInt())
            }
        }
    }
}