package ink.ptms.ovile.ingame

import ink.ptms.ovile.OvileAPI
import ink.ptms.ovile.ingame.action.block.BlockAction
import ink.ptms.ovile.ingame.action.block.PlayerBlockAction
import ink.ptms.ovile.ingame.action.region.PlayerRegionAction
import ink.ptms.ovile.ingame.action.region.RegionAction
import ink.ptms.ovile.util.Version
import taboolib.common.LifeCycle
import taboolib.common.inject.Injector
import taboolib.common.platform.Awake
import java.util.function.Supplier

/**
 * Ovile
 * ink.ptms.ovile.ingame.PluginLoader
 *
 * @author 坏黑
 * @since 2022/5/2 09:18
 */
@Awake
object Loader : Injector.Classes {

    override fun inject(clazz: Class<*>, instance: Supplier<*>) {
        when {
            clazz.isAnnotationPresent(RegionAction::class.java) -> {
                OvileAPI.registerRegionAction(instance.get() as PlayerRegionAction<*>, clazz.getAnnotation(RegionAction::class.java).bind.java)
            }
            clazz.isAnnotationPresent(BlockAction::class.java) -> {
                when (clazz.getAnnotation(BlockAction::class.java).version) {
                    BlockAction.Version.BLOCK_DATA -> {
                        // 高版本（1.13+）
                        if (!Version.oldVersionSupport) {
                            OvileAPI.registerBlockAction(instance.get() as PlayerBlockAction)
                        }
                    }
                    BlockAction.Version.BLOCK_STATE -> {
                        // 低版本（1.13-）
                        if (Version.oldVersionSupport) {
                            OvileAPI.registerBlockAction(instance.get() as PlayerBlockAction)
                        }
                    }
                }
            }
        }
    }

    override fun postInject(clazz: Class<*>, instance: Supplier<*>) {
    }

    override val lifeCycle: LifeCycle
        get() = LifeCycle.LOAD

    override val priority: Byte
        get() = 0
}