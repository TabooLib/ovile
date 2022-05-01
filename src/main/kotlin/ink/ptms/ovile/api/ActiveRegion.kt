package ink.ptms.ovile.api

import org.bukkit.entity.Player
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

    fun getPlayers(): Set<Player> {
        return players
    }


}