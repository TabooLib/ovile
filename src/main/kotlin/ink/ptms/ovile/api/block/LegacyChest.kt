package ink.ptms.ovile.api.block

import org.bukkit.block.BlockFace

/**
 * Ovile
 * ink.ptms.ovile.api.block.LegacyChest
 *
 * @author 坏黑
 * @since 2022/5/5 15:13
 */
class LegacyChest(override var facing: BlockFace, var type: Type) : LegacyDirectional() {

    override fun getState(): Int {
        return facingMap[facing] ?: 0
    }

    enum class Type {

        SINGLE, LEFT, RIGHT
    }

    companion object {

        // 在 Paper 1.12.2 服务端环境下测试的结果, 其他子版本 & 低版本未确认是否一致
        val facingMap = mapOf(BlockFace.NORTH to 2, BlockFace.SOUTH to 3, BlockFace.WEST to 4, BlockFace.EAST to 5)

        fun wrapper(data: Int): LegacyChest {
            TODO()
        }
    }
}