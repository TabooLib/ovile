package ink.ptms.ovile.util.extension

import ink.ptms.ovile.Ovile
import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.World
import java.util.*

fun World.createRegion(min: Location, max: Location): Region? {
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