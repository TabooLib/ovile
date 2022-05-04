package ink.ptms.ovile.ingame.nms

import ink.ptms.ovile.api.RegionBlock
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player

class BlockDataImpl(val data: BlockData) : RegionBlock {

    override fun material(): Material {
        return data.material
    }

    override fun sendTo(player: Player, location: Location) {
        player.sendBlockChange(location, data)
    }
}