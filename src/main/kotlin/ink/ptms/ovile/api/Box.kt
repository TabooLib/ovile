package ink.ptms.ovile.api

import org.bukkit.Location
import kotlin.math.max
import kotlin.math.min

/**
 * @author sky
 * @since 2021/1/25 3:02 上午
 */
abstract class Box(val minX: Double, val minY: Double, val minZ: Double, val maxX: Double, val maxY: Double, val maxZ: Double) {

    fun contains(v: Location): Boolean {
        return contains(v.x, v.y, v.z)
    }

    fun contains(x: Double, y: Double, z: Double): Boolean {
        return x >= minX && x < maxX && y >= minY && y < maxY && z >= minZ && z < maxZ
    }

    fun contains(other: Box): Boolean {
        return contains(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ)
    }

    fun contains(min: Location, max: Location): Boolean {
        return contains(min(min.x, max.x), min(min.y, max.y), min(min.z, max.z), max(min.x, max.x), max(min.y, max.y), max(min.z, max.z))
    }

    fun contains(minX: Double, minY: Double, minZ: Double, maxX: Double, maxY: Double, maxZ: Double): Boolean {
        return this.minX <= minX && this.maxX >= maxX && this.minY <= minY && this.maxY >= maxY && this.minZ <= minZ && this.maxZ >= maxZ
    }

    fun getSize(): Double {
        return (getXSize() + getYSize() + getZSize()) / 3.0
    }

    fun getXSize(): Double {
        return maxX - minX
    }

    fun getYSize(): Double {
        return maxY - minY
    }

    fun getZSize(): Double {
        return maxZ - minZ
    }

    override fun toString(): String {
        return "Box(minX=$minX, minY=$minY, minZ=$minZ, maxX=$maxX, maxY=$maxY, maxZ=$maxZ)"
    }
}