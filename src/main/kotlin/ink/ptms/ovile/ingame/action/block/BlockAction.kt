package ink.ptms.ovile.ingame.action.block

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.block.BlockAction
 *
 * @author 坏黑
 * @since 2022/5/2 09:14
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BlockAction(val version: Version) {

    enum class Version {

        BLOCK_DATA, BLOCK_STATE
    }
}