package ink.ptms.ovile.util.extension

import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

fun Location.getRegion(): Region? {
    return world?.getRegion(this)
}

fun Location.toCenter(offset: Double): Location {
    return Location(world, blockX + offset, blockY + offset, blockZ + offset)
}

fun Location.relative(direction: BlockFace): Location {
    return this.clone().add(Vector(direction.modX, direction.modY, direction.modZ))
}