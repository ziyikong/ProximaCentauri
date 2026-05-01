package Proxima.block;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.content.UnitTypes;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;
import Proxima.content.ProximaUnitTypes;

/**
 * 比邻星核心方块
 * 继承自 CoreBlock，作为星球的起始核心和科技树起点
 */
public class ProximaCoreBlock extends CoreBlock {

    /** 核心颜色 */
    public Color coreColor = Color.valueOf("87CEEB");

    public ProximaCoreBlock(String name) {
        super(name);

        // 基础配置
        requirements(Category.effect, BuildVisibility.coreZoneOnly, ItemStack.with(
            mindustry.content.Items.copper, 1000,
            mindustry.content.Items.lead, 800
        ));

        alwaysUnlocked = true;

        // 核心配置
        isFirstTier = true;
        unitType = ProximaUnitTypes.proximaCoreMech;
        health = 1100;
        itemCapacity = 4000;
        size = 3;
        buildCostMultiplier = 2f;
        unitCapModifier = 8;

        // 环境配置
        envEnabled |= Env.terrestrial | Env.space | Env.underwater | Env.any;

        // 外观配置
        flags = EnumSet.of(BlockFlag.core);
        priority = mindustry.entities.TargetPriority.core;
    }
}
