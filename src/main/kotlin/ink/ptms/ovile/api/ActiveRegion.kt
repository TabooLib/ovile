package ink.ptms.ovile.api

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
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

    internal val blocks = ConcurrentHashMap<Location, ActiveRegionBlock>()
    internal val players = CopyOnWriteArraySet<Player>()
    internal val entities = CopyOnWriteArraySet<Entity>()

    init {
        blocks.putAll(region.blocks.map { it.key to ActiveRegionBlock(it.value) { RegionBlock.of(it.key.block) } })
    }

    fun getPlayers(): Set<Player> {
        return players
    }

    fun getExternalPlayers(): Set<Player> {
        return Bukkit.getOnlinePlayers().filter { it !in players }.toSet()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : RegionBlock>getBlock(location: Location): T? {
        return blocks[location]?.block as T?
    }

    /**
     * 放置方块
     * 发包替换方块类型，并储存该位置原本的类型
     * 当玩家离开区域时恢复所有方块
     */
    fun setBlock(location: Location, data: RegionBlock) {
        val loc = location.clone()
        blocks[loc] = ActiveRegionBlock(data) { region.blocks[loc] ?: RegionBlock.of(loc.block) }
        players.forEach { data.sendTo(it, location) }
    }

    fun displayBlocks(player: Player) {
        blocks.forEach { it.value.block.sendTo(player, it.key) }
    }

    fun destroyBlocks(player: Player) {
        blocks.forEach { it.value.originBlock.get().sendTo(player, it.key) }
    }
}