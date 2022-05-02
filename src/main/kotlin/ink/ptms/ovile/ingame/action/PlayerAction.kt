package ink.ptms.ovile.ingame.action

import kotlin.reflect.KClass

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.PlayerAction
 *
 * @author 坏黑
 * @since 2022/5/2 09:14
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PlayerAction(val bind: KClass<*>)