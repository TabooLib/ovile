package ink.ptms.ovile.api.event.packet

import org.bukkit.entity.Player
import org.bukkit.util.Vector
import taboolib.platform.type.BukkitProxyEvent
import taboolib.type.BukkitEquipment

/**
 * Ovile
 * ink.ptms.ovile.api.event.OvilePlayerBlockPlacePacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
class OvilePlayerUseEntityPacket(val player: Player, val entityId: Int, val action: Action, val hand: Hand?, val location: Vector?) : BukkitProxyEvent() {

    val entity by lazy { player.world.entities.firstOrNull { it.entityId == entityId } }

    enum class Hand {

        MAIN_HAND, OFF_HAND;

        fun toBukkit(): BukkitEquipment {
            return when (this) {
                MAIN_HAND -> BukkitEquipment.HAND
                OFF_HAND -> BukkitEquipment.OFF_HAND
            }
        }
    }

    enum class Action {

        ATTACK, INTERACT
    }
}