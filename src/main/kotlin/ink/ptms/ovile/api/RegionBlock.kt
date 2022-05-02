package ink.ptms.ovile.api

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
    
    fun sendTo(player: Player, location: Location)

    /**
     * 高版本实现（BlockData）
     */
    class BlockDataImpl(val data: BlockData) : RegionBlock {

        override fun sendTo(player: Player, location: Location) {
            player.sendBlockChange(location, data)
        }
    }

    /**
     * 低版本实现（BlockState）
     */
    class BlockStateImpl(val material: Material, val data: Int) : RegionBlock {

        override fun sendTo(player: Player, location: Location) {
            player.sendBlockChange(location, material, data.toByte())
        }
    }
    
    companion object {

        val air = if (MinecraftVersion.majorLegacy > 11200) {
            BlockDataImpl(Material.AIR.createBlockData())
        } else {
            BlockStateImpl(Material.AIR, 0)
        }

        fun of(data: BlockData): RegionBlock {
            return BlockDataImpl(data)
        }
        
        fun of(material: Material, data: Int): RegionBlock {
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