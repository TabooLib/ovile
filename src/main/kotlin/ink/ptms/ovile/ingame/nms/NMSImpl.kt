package ink.ptms.ovile.ingame.nms

import net.minecraft.core.BaseBlockPosition
import org.bukkit.util.Vector

/**
 * Ovile
 * ink.ptms.ovile.ingame.nms.NMSImpl
 *
 * @author 坏黑
 * @since 2022/5/1 20:18
 */
class NMSImpl : NMS() {

    override fun blockPositionToVector(pos: Any): Vector {
        val p = (pos as net.minecraft.server.v1_12_R1.BaseBlockPosition)
        return Vector(p.x, p.y, p.z)
    }
}