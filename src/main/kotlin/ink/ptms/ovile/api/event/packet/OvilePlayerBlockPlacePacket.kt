package ink.ptms.ovile.api.event.packet

import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent
import taboolib.type.BukkitEquipment

/**
 * Ovile
 * ink.ptms.ovile.api.event.OvilePlayerBlockPlacePacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
class OvilePlayerBlockPlacePacket(val player: Player, val hand: Hand) : BukkitProxyEvent() {

    enum class Hand {

        MAIN_HAND, OFF_HAND;

        fun toBukkit(): BukkitEquipment {
            return when (this) {
                MAIN_HAND -> BukkitEquipment.HAND
                OFF_HAND -> BukkitEquipment.OFF_HAND
            }
        }
    }
}