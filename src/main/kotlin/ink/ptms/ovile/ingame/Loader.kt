package ink.ptms.ovile.ingame

import ink.ptms.ovile.Ovile
import ink.ptms.ovile.ingame.action.PlayerAction
import ink.ptms.ovile.ingame.action.PlayerRegionAction
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
        if (clazz.isAnnotationPresent(PlayerAction::class.java)) {
            Ovile.registerAction(instance.get() as PlayerRegionAction<*>, clazz.getAnnotation(PlayerAction::class.java).bind.java)
        }
    }

    override fun postInject(clazz: Class<*>, instance: Supplier<*>) {
    }

    override val lifeCycle: LifeCycle
        get() = LifeCycle.LOAD

    override val priority: Byte
        get() = 0
}