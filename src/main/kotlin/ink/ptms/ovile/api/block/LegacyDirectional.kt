package ink.ptms.ovile.api.block

import org.bukkit.block.BlockFace

/**
 * Ovile
 * ink.ptms.ovile.api.block.LegacyDirectional
 *
 * @author 坏黑
 * @since 2022/5/5 15:15
 */
abstract class LegacyDirectional : LegacyBlockData {

    abstract var facing: BlockFace
}