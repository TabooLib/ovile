package ink.ptms.ovile.api

import java.util.function.Supplier

/**
 * Ovile
 * ink.ptms.ovile.api.ActiveRegionBlock
 *
 * @author 坏黑
 * @since 2022/5/2 15:42
 */
class ActiveRegionBlock(val block: RegionBlock, val originBlock: Supplier<RegionBlock>)