package ink.ptms.ovile.ingame.nms

import ink.ptms.ovile.util.Version
import net.minecraft.server.v1_16_R1.Vec3D
import net.minecraft.server.v1_16_R3.BlockPosition
import net.minecraft.server.v1_16_R3.Blocks
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import taboolib.module.nms.sendPacket

/**
 * Ovile
 * ink.ptms.ovile.ingame.nms.NMSImpl
 *
 * @author 坏黑
 * @since 2022/5/1 20:18
 */
class NMSImpl : NMS() {

    override fun sendBlockAction(player: Player, block: Location, a: Int, b: Int) {
        if (Version.isLegacyVersion) {
            val position = net.minecraft.server.v1_12_R1.BlockPosition(block.x, block.y, block.z)
            player.sendPacket(net.minecraft.server.v1_12_R1.PacketPlayOutBlockAction(position, net.minecraft.server.v1_12_R1.Blocks.CHEST, a, b))
        } else {
            val position = BlockPosition(block.x, block.y, block.z)
            player.sendPacket(PacketPlayOutBlockAction(position, Blocks.CHEST, a, b))
        }
    }

    override fun blockPositionToVector(pos: Any): Vector {
        return Vector(((pos as net.minecraft.server.v1_12_R1.BaseBlockPosition)).x, pos.y, pos.z)
    }

    override fun parseVec3d(obj: Any): Vector {
        return Vector((obj as Vec3D).x, obj.y, obj.z)
    }
}