package ink.ptms.ovile.ingame

import ink.ptms.ovile.api.event.packet.OvilePlayerBlockPlacePacket
import ink.ptms.ovile.api.event.packet.OvilePlayerDigBlockPacket
import ink.ptms.ovile.api.event.packet.OvilePlayerUseEntityPacket
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import ink.ptms.ovile.ignoreBlockChange
import ink.ptms.ovile.ingame.nms.NMS
import ink.ptms.ovile.isIgnoreBlockChange
import ink.ptms.ovile.refreshBlock
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange
import net.minecraft.network.protocol.game.PacketPlayOutMultiBlockChange
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo
import org.bukkit.Location
import org.bukkit.util.Vector
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.common.reflect.Reflex.Companion.getProperty
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.PacketReceiveEvent
import taboolib.module.nms.PacketSendEvent
import taboolib.platform.util.isNotAir

/**
 * Ovile
 * ink.ptms.ovile.ingame.ListenerPlayerPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:17
 */
object ListenerPlayerPacket {

//    @SubscribeEvent
//    fun e(e: PacketSendEvent) {
//        when (e.packet.name) {
//            "PacketPlayOutBlockChange" -> {
//                val vector = NMS.INSTANCE.blockPositionToVector(e.packet.read<Any>("a")!!)
//                val location = vector.toLocation(e.player.world)
//                if (e.player.isIgnoreBlockChange(location)) {
//                    e.isCancelled = true
//                }
//            }
//        }
//    }

    @SubscribeEvent
    fun e(e: PacketReceiveEvent) {
        when (e.packet.name) {
            "PacketPlayInBlockPlace" -> {
                val hand = OvilePlayerBlockPlacePacket.Hand.valueOf(e.packet.read<Any>("a").toString().uppercase())
                if (OvilePlayerBlockPlacePacket(e.player, hand).call()) {
                    return
                }
                e.isCancelled = true
            }
            "PacketPlayInBlockDig" -> {
                val a = e.packet.read<Any>("a")!!
                val vector = NMS.INSTANCE.blockPositionToVector(a)
                val location = vector.toLocation(e.player.world)
                val direction = OvilePlayerDigBlockPacket.Direction.valueOf(e.packet.read<Any>("b").toString().uppercase())
                val type = OvilePlayerDigBlockPacket.Type.values()[e.packet.read<Enum<*>>("c")!!.ordinal]
                val event = OvilePlayerDigBlockPacket(e.player, location, direction, type)
                if (event.call()) {
                    return
                }
                e.isCancelled = true
                // 刷新方块
                if (event.isRefreshBlock) {
                    submit(delay = 1) { e.player.refreshBlock(location) }
                }
            }
            "PacketPlayInUseItem" -> {
                val a = e.packet.read<Any>("a")!!
                val location: Location
                val hand: OvilePlayerUseItemPacket.Hand
                val direction: OvilePlayerUseItemPacket.Direction
                val event: OvilePlayerUseItemPacket
                if (a.javaClass.simpleName == "BlockPosition") {
                    val vector = NMS.INSTANCE.blockPositionToVector(a)
                    location = vector.toLocation(e.player.world)
                    direction = OvilePlayerUseItemPacket.Direction.valueOf(e.packet.read<Any>("b").toString().uppercase())
                    hand = OvilePlayerUseItemPacket.Hand.valueOf(e.packet.read<Any>("c").toString().uppercase())
                    event = OvilePlayerUseItemPacket(e.player, location, hand, direction)
                } else {
                    val vector = NMS.INSTANCE.blockPositionToVector(a.getProperty<Any>("c")!!)
                    location = vector.toLocation(e.player.world)
                    hand = OvilePlayerUseItemPacket.Hand.valueOf(e.packet.read<Any>("b").toString().uppercase())
                    direction = OvilePlayerUseItemPacket.Direction.valueOf(a.getProperty<Any>("b")!!.toString().uppercase())
                    event = OvilePlayerUseItemPacket(e.player, location, hand, direction)
                }
                if (event.call()) {
                    return
                }
                e.isCancelled = true
                // 刷新方块
                if (event.isRefreshBlock) {
                    submit(delay = 1) {
                        e.player.refreshBlock(location)
                        e.player.refreshBlock(location.block.getRelative(direction.toBukkit()).location)
                    }
                }
            }
            "PacketPlayInUseEntity" -> {
                val entityId = e.packet.read<Int>("a")!!
                val event: OvilePlayerUseEntityPacket?
                if (MinecraftVersion.isUniversal) {
                    val action = e.packet.read<Any>("action")!!
                    event = when (action.javaClass.simpleName) {
                        // ATTACK
                        "d" -> {
                            OvilePlayerUseEntityPacket(e.player, entityId, OvilePlayerUseEntityPacket.Action.ATTACK, null, null)
                        }
                        // INTERACT_AT
                        "e" -> {
                            val location = action.getProperty<Any>("location")
                            val hand = OvilePlayerUseEntityPacket.Hand.valueOf(action.getProperty<Any>("hand").toString().uppercase())
                            val vector = if (location == null) Vector(0, 0, 0) else NMS.INSTANCE.parseVec3d(location)
                            OvilePlayerUseEntityPacket(e.player, entityId, OvilePlayerUseEntityPacket.Action.INTERACT, hand, vector)
                        }
                        else -> null
                    }
                } else {
                    event = when (e.packet.read<Any>("action").toString()) {
                        "ATTACK" -> {
                            OvilePlayerUseEntityPacket(e.player, entityId, OvilePlayerUseEntityPacket.Action.ATTACK, null, null)
                        }
                        "INTERACT_AT" -> {
                            val location = e.packet.read<Any>("c")
                            val hand = OvilePlayerUseEntityPacket.Hand.valueOf(e.packet.read<Any>("d").toString().uppercase())
                            val vector = if (location == null) Vector(0, 0, 0) else NMS.INSTANCE.parseVec3d(location)
                            OvilePlayerUseEntityPacket(e.player, entityId, OvilePlayerUseEntityPacket.Action.INTERACT, hand, vector)
                        }
                        else -> null
                    }
                }
                if (event == null || event.call()) {
                    return
                }
                e.isCancelled = true
            }
        }
    }
}