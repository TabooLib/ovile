package ink.ptms.ovile.util.extension

import ink.ptms.ovile.OvileAPI.ignoreBlockChangeMap
import ink.ptms.ovile.OvileAPI.notifyBaffle
import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.isNotAir
import taboolib.platform.util.sendLang
import java.util.concurrent.CopyOnWriteArraySet

/**
 * 获取玩家手持物品
 */
fun Player.heldItem(): ItemStack {
    return if (itemInHand.isNotAir()) itemInHand else inventory.itemInOffHand
}

/**
 * 获取玩家所在区域
 */
fun Player.getRegion(): Region? {
    return location.getRegion()
}

/**
 * 获取玩家所在区域实例
 */
fun Player.getActiveRegion(): ActiveRegion? {
    return location.getRegion()?.getActiveRegion(this)
}

/**
 * 刷新玩家视角下坐标处对应方块
 */
fun Player.refreshBlock(location: Location) {
    try {
        sendBlockChange(location, location.block.blockData)
    } catch (ex: NoSuchMethodError) {
        sendBlockChange(location, location.block.type, location.block.data)
    }
}

/**
 * 发送信息
 */
fun Player.sendRegionNotify(block: Location, direction: BlockFace = BlockFace.SELF, node: String = "player-action-across-regions", vararg args: String) {
    if (notifyBaffle.hasNext(name)) {
        sendLang(node, *args)
        spawnParticle(Particle.SMOKE_NORMAL, block.relative(direction).toCenter(0.5), 10, 0.0, 0.0, 0.0, 0.0)
    }
}

/**
 * 获取玩家视角
 * 在服务端 1.12.2 环境下没有获取方向方法，因此使用视角判断
 */
fun Player.getFacingDirection(): BlockFace {
    var yaw = location.yaw
    if (yaw < 0.0) yaw += 360.0f
    if (yaw >= 315 || yaw < 45) {
        return BlockFace.SOUTH
    } else if (yaw < 135) {
        return BlockFace.WEST
    } else if (yaw < 225) {
        return BlockFace.NORTH
    } else if (yaw < 315) {
        return BlockFace.EAST
    }
    return BlockFace.NORTH
}

internal fun Player.ignoreBlockChange(location: Location) {
    ignoreBlockChangeMap.computeIfAbsent(name) { CopyOnWriteArraySet() }.add(location)
}

internal fun Player.isIgnoreBlockChange(location: Location): Boolean {
    return ignoreBlockChangeMap[name]?.contains(location) == true
}