package ink.ptms.ovile.api.event.packet

import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Ovile
 * ink.ptms.ovile.api.event.OvilePlayerDigBlockPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
class OvilePlayerDigBlockPacket(val player: Player, val location: Location, val direction: Direction, val type: Type) : BukkitProxyEvent() {

    var isRefreshBlock = true

    enum class Type {

        START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM, SWAP_ITEM_WITH_OFFHAND
    }

    enum class Direction {

        DOWN, UP, NORTH, SOUTH, WEST, EAST;

        fun toBukkit(): BlockFace {
            return BlockFace.valueOf(name.uppercase())
        }
    }
}