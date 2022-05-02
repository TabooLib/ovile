package ink.ptms.ovile.ingame.action.region

import ink.ptms.ovile.*
import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import ink.ptms.ovile.matchBlockAction
import org.bukkit.Location
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.module.nms.MinecraftVersion
import taboolib.platform.util.isNotAir

/**
 * Ovile
 * ink.ptms.ovile.ingame.action.player.PlayerRegionActionUseItemPacket
 *
 * @author 坏黑
 * @since 2022/5/1 20:21
 */
@RegionAction(OvilePlayerUseItemPacket::class)
object PlayerRegionActionUseItemPacket : PlayerRegionAction<OvilePlayerUseItemPacket> {

    override fun handle(player: Player, location: Location, region: Region, active: ActiveRegion, event: OvilePlayerUseItemPacket) {
        event.isCancelled = true
        event.region = active
        val blockFace = event.direction.toBukkit()
        val relativeLocation = location.block.getRelative(blockFace).location
        val relativeLocationRegion = relativeLocation.getRegion()
        if (relativeLocationRegion != region) {
            player.sendRegionNotify(location, blockFace)
            return
        }
        val heldItem = player.heldItem()
        if (heldItem.isNotAir() && heldItem.type.isBlock) {
            player.sendMessage("摆放方块")
            val matchBlockAction = heldItem.matchBlockAction()
            if (matchBlockAction == null) {
                player.sendRegionNotify(location, blockFace, "player-action-no-implementation", heldItem.blockActionType())
                return
            }
            matchBlockAction.placeBlock(player, heldItem, relativeLocation, active)
        } else {
            player.sendMessage("交互方块")
        }
    }
}