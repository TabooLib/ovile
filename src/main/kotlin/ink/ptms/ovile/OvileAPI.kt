package ink.ptms.ovile

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.PlayerBlockAction
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import taboolib.common5.Baffle
import taboolib.module.nms.MinecraftVersion
import taboolib.platform.util.isNotAir
import taboolib.platform.util.sendLang
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.TimeUnit

internal val notifyBaffle = Baffle.of(200, TimeUnit.MILLISECONDS)

internal val ignoreBlockChangeMap = ConcurrentHashMap<String, MutableSet<Location>>()

fun World.createRegion(min: Location, max: Location): Region {
    return Ovile.create(min, max)
}

fun World.getRegion(id: UUID): Region? {
    return Ovile.regions[name]?.firstOrNull { it.id == id }
}

fun World.getRegion(location: Location): Region? {
    return Ovile.regions[name]?.firstOrNull { it.contains(location) }
}

fun World.getRegions(): List<Region> {
    return Ovile.regions[name] ?: emptyList()
}

fun Location.getRegion(): Region? {
    return world?.getRegion(this)
}

fun Location.toCenter(offset: Double): Location {
    return Location(world, blockX + offset, blockY + offset, blockZ + offset)
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

fun Player.heldItem(): ItemStack {
    return if (itemInHand.isNotAir()) itemInHand else inventory.itemInOffHand
}

fun BlockFace.right(): BlockFace {
    return if (ordinal + 1 > 3) BlockFace.NORTH else BlockFace.values()[ordinal + 1]
}

fun BlockFace.left(): BlockFace {
    return right().oppositeFace
}

fun Location.relative(direction: BlockFace): Location {
    return this.clone().add(Vector(direction.modX, direction.modY, direction.modZ))
}

internal fun Player.ignoreBlockChange(location: Location) {
    ignoreBlockChangeMap.computeIfAbsent(name) { CopyOnWriteArraySet() }.add(location)
}

internal fun Player.isIgnoreBlockChange(location: Location): Boolean {
    return ignoreBlockChangeMap[name]?.contains(location) == true
}

internal fun RegionBlock.matchBlockAction(): PlayerBlockAction? {
    return Ovile.blockActions.firstOrNull { it.match(this) }
}

internal fun ItemStack.matchBlockAction(): PlayerBlockAction? {
    return Ovile.blockActions.firstOrNull { it.match(this) }
}

internal fun RegionBlock.blockActionType(): String {
    return when (this) {
        is RegionBlock.BlockDataImpl -> data.javaClass.simpleName
        is RegionBlock.BlockStateImpl -> material.name
        else -> "Error: ${javaClass.name}"
    }
}

internal fun ItemStack.blockActionType(): String {
    return if (MinecraftVersion.majorLegacy > 11200) type.createBlockData().javaClass.simpleName else type.name
}