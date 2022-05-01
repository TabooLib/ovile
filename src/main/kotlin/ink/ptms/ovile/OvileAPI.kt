package ink.ptms.ovile

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

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