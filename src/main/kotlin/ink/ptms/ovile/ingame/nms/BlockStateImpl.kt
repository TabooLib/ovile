package ink.ptms.ovile.ingame.nms

import ink.ptms.ovile.api.RegionBlock
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * 区域方块在低版本 (1.13 -) 的实现
 */
class BlockStateImpl(val material: Material, val data: Int) : RegionBlock {

    override val blockActionType: String
        get() = material.name

    override fun material(): Material {
        return material
    }

    override fun sendTo(player: Player, location: Location) {
        player.sendBlockChange(location, material, data.toByte())
    }
}