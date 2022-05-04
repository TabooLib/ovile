package ink.ptms.ovile.ingame.nms

import ink.ptms.ovile.api.RegionBlock
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

class BlockStateImpl(val material: Material, val data: Int) : RegionBlock {

    override fun material(): Material {
        return material
    }

    override fun sendTo(player: Player, location: Location) {
        player.sendBlockChange(location, material, data.toByte())
    }
}