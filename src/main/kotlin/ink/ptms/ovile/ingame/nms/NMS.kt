package ink.ptms.ovile.ingame.nms

import org.bukkit.util.Vector
import taboolib.module.nms.nmsProxy

/**
 * Ovile
 * ink.ptms.ovile.ingame.nms.NMS
 *
 * @author 坏黑
 * @since 2022/5/1 20:17
 */
abstract class NMS {

    abstract fun blockPositionToVector(pos: Any): Vector

    companion object {

        val INSTANCE = nmsProxy<NMS>()
    }
}