package ink.ptms.ovile.ingame

import ink.ptms.ovile.api.event.packet.OvilePlayerDigBlockPacket
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import ink.ptms.ovile.ingame.nms.NMS
import ink.ptms.ovile.refreshBlock
import org.bukkit.Location
import org.bukkit.util.Vector
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.common.reflect.Reflex
import taboolib.common.reflect.Reflex.Companion.getProperty
import taboolib.common.reflect.Reflex.Companion.invokeMethod
import taboolib.common.reflect.ReflexClass
import taboolib.module.nms.PacketReceiveEvent
import taboolib.module.nms.nmsClass
import taboolib.platform.util.isNotAir

/**
 * Ovile
 * ink.ptms.ovile.ingame.ListenerPlayerPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:17
 */
object ListenerPlayerPacket {

    @SubscribeEvent
    fun e(e: PacketReceiveEvent) {
        when (e.packet.name) {
            "PacketPlayInBlockDig" -> {
                val a = e.packet.read<Any>("a")!!
                val vector = positionToVector(a)
                val direction = OvilePlayerDigBlockPacket.Direction.valueOf(e.packet.read<Any>("b").toString().uppercase())
                val type = OvilePlayerDigBlockPacket.Type.values()[e.packet.read<Enum<*>>("c")!!.ordinal]
                if (OvilePlayerDigBlockPacket(e.player, Location(e.player.world, vector.x, vector.y, vector.z), direction, type).call()) {
                    return
                }
                e.isCancelled = true
            }
            "PacketPlayInUseItem" -> {
                val a = e.packet.read<Any>("a")!!
                val location: Location
                val hand: OvilePlayerUseItemPacket.Hand
                val direction: OvilePlayerUseItemPacket.Direction
                if (a.javaClass.simpleName == "BlockPosition") {
                    val vector = positionToVector(a)
                    location = Location(e.player.world, vector.x, vector.y, vector.z)
                    direction = OvilePlayerUseItemPacket.Direction.valueOf(e.packet.read<Any>("b").toString().uppercase())
                    hand = OvilePlayerUseItemPacket.Hand.valueOf(e.packet.read<Any>("c").toString().uppercase())
                    if (OvilePlayerUseItemPacket(e.player, location, hand, direction).call()) {
                        return
                    }
                } else {
                    val vector = positionToVector(a.getProperty<Any>("c")!!)
                    location = Location(e.player.world, vector.x, vector.y, vector.z)
                    hand = OvilePlayerUseItemPacket.Hand.valueOf(e.packet.read<Any>("b").toString().uppercase())
                    direction = OvilePlayerUseItemPacket.Direction.valueOf(a.getProperty<Any>("b")!!.toString().uppercase())
                    if (OvilePlayerUseItemPacket(e.player, location, hand, direction).call()) {
                        return
                    }
                }
                e.isCancelled = true
                // 刷新方块
                val item = hand.toBukkit().getItem(e.player)
                if (item.isNotAir() && item!!.type.isBlock) {
                    submit { e.player.refreshBlock(location.block.getRelative(direction.toBukkit()).location) }
                }
            }
        }
    }

    fun positionToVector(any: Any): Vector {
        return NMS.INSTANCE.blockPositionToVector(any)
    }
}