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

internal fun Player.ignoreBlockChange(location: Location) {
    ignoreBlockChangeMap.computeIfAbsent(name) { CopyOnWriteArraySet() }.add(location)
}

internal fun Player.isIgnoreBlockChange(location: Location): Boolean {
    return ignoreBlockChangeMap[name]?.contains(location) == true
}

fun Player.heldItem(): ItemStack {
    return if (itemInHand.isNotAir()) itemInHand else inventory.itemInOffHand
}

fun Player.getRegion(): Region? {
    return location.getRegion()
}

fun Player.getActiveRegion(): ActiveRegion? {
    return location.getRegion()?.getActiveRegion(this)
}

fun Player.refreshBlock(location: Location) {
    try {
        sendBlockChange(location, location.block.blockData)
    } catch (ex: NoSuchMethodError) {
        sendBlockChange(location, location.block.type, location.block.data)
    }
}

fun Player.sendRegionNotify(block: Location, direction: BlockFace = BlockFace.SELF, node: String = "player-action-across-regions", vararg args: String) {
    if (notifyBaffle.hasNext(name)) {
        sendLang(node, *args)
        spawnParticle(Particle.SMOKE_NORMAL, block.relative(direction).toCenter(0.5), 10, 0.0, 0.0, 0.0, 0.0)
    }
}