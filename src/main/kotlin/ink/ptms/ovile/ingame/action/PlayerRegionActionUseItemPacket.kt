package ink.ptms.ovile.ingame.action

import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import ink.ptms.ovile.getRegion
import ink.ptms.ovile.heldItem
import ink.ptms.ovile.sendAcrossNotify
import org.bukkit.Location
import org.bukkit.block.data.Directional
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.platform.util.isNotAir

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.PlayerRegionActionUseItemPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
@PlayerAction(OvilePlayerUseItemPacket::class)
object PlayerRegionActionUseItemPacket : PlayerRegionAction<OvilePlayerUseItemPacket> {

    override fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: OvilePlayerUseItemPacket) {
        event.isCancelled = true
        event.isRefreshBlock = false
        val blockFace = event.direction.toBukkit()
        val relativeLocation = location.block.getRelative(blockFace).location
        val relativeLocationRegion = relativeLocation.getRegion()
        if (relativeLocationRegion != region) {
            player.sendAcrossNotify(location, blockFace)
            return
        }
        relativeLocation.block.state
        val heldItem = player.heldItem()
        if (heldItem.isNotAir() && heldItem.type.isBlock) {
            val data = heldItem.type.createBlockData {
                if (it is Directional) {
                    it.facing = player.facing.oppositeFace
                }
            }
            submit(delay = 2) { active.setBlock(relativeLocation, data) }
            player.sendMessage("摆放方块")
        } else {
            player.sendMessage("交互方块")
        }
    }
}