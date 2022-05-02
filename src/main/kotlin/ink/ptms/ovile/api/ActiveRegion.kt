package ink.ptms.ovile.api

import ink.ptms.ovile.left
import ink.ptms.ovile.relative
import ink.ptms.ovile.right
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.data.type.Chest
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Ovile
 * ink.ptms.ovile.api.ActiveRegion
 *
 * @author 坏黑
 * @since 2022/5/1 20:49
 */
class ActiveRegion(val region: Region) {

    internal val players = CopyOnWriteArraySet<Player>()
    internal val blocks = ConcurrentHashMap<Location, RegionBlock>()

    init {
        blocks.putAll(region.defaultBlocks)
    }

    fun getPlayers(): Set<Player> {
        return players
    }

    fun getExternalPlayers(): Set<Player> {
        return Bukkit.getOnlinePlayers().filter { it !in players }.toSet()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : RegionBlock>getBlock(location: Location): T? {
        return blocks[location] as T?
    }

    fun setBlock(location: Location, data: RegionBlock?) {
        if (data == null || data == RegionBlock.air) {
            blocks.remove(location.clone())
            players.forEach { RegionBlock.air.sendTo(it, location) }
        } else {
            blocks[location.clone()] = data
            players.forEach { data.sendTo(it, location) }
        }
    }

    fun displayBlocks(player: Player) {
        blocks.forEach { it.value.sendTo(player, it.key) }
    }

    fun destroyBlocks(player: Player) {
        blocks.forEach { RegionBlock.of(it.key.block).sendTo(player, it.key) }
    }
}