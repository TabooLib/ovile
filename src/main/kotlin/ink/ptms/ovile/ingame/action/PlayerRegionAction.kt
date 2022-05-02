package ink.ptms.ovile.ingame.action

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.PlayerRegionAction
 *
 * @author 坏黑
 * @since 2022/5/2 09:10
 */
interface PlayerRegionAction<T> {

    fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: T)
}