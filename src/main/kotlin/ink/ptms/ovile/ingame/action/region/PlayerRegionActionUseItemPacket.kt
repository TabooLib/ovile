package ink.ptms.ovile.ingame.action.region

import ink.ptms.ovile.*
import ink.ptms.ovile.api.ActiveRegion
import ink.ptms.ovile.api.Region
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.api.event.packet.OvilePlayerUseItemPacket
import org.bukkit.Location
import org.bukkit.entity.Player
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
        // 判断摆放位置是否位于当前区域
        val relativeLocation = location.block.getRelative(blockFace).location
        val relativeLocationRegion = relativeLocation.getRegion()
        if (relativeLocationRegion != region) {
            player.sendRegionNotify(location, blockFace)
            return
        }
        val heldItem = player.heldItem()
        if (heldItem.isNotAir() && heldItem.type.isBlock) {
            // 获取实现
            val matchBlockAction = heldItem.matchBlockAction()
            if (matchBlockAction == null) {
                player.sendRegionNotify(location, blockFace, "player-action-no-implementation", heldItem.blockActionType())
                return
            }
            // 摆放位置存在实体方块则触发合并逻辑
            val original = active.getBlock(relativeLocation) ?: RegionBlock.of(relativeLocation.block)
            if (original.material().isSolid) {
                matchBlockAction.mergeBlock(player, heldItem, relativeLocation, active, blockFace, original)
            } else {
                matchBlockAction.placeBlock(player, heldItem, relativeLocation, active, blockFace)
            }
            player.sendMessage("摆放方块")
        } else {
            player.sendMessage("交互方块")
        }
    }
}