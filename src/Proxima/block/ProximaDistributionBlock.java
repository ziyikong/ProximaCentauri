package Proxima.block;

import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

/**
 * Proxima物流基础方块类
 * 作为所有自定义物流方块的父类，提供灵活的配置选项
 */
public class ProximaDistributionBlock extends Block {

    // ==================== 输入输出配置 ====================
    
    /** 是否允许从同类方块输入物品 */
    public boolean sameClassInput = true;
    
    /** 是否允许向同类方块输出物品 */
    public boolean sameClassOutput = true;
    
    /** 是否允许从不同类方块输入物品 */
    public boolean differentClassInput = true;
    
    /** 是否允许向不同类方块输出物品 */
    public boolean differentClassOutput = true;
    
    // ==================== 存储配置 ====================
    
    /** 是否可以存储物品 */
    public boolean canStoreItems = true;
    
    /** 存储物品的最大数量 */
    public int storageItemCapacity = 10;
    
    /** 是否为纯粹的存储方块（不参与传输） */
    public boolean isStorageBlock = false;
    
    // ==================== 传送带配置 ====================
    
    /** 是否为传送带类型 */
    public boolean isConveyor = false;
    
    /** 传送带速度（物品/秒） */
    public float conveyorSpeed = 10f;
    
    /** 是否自动连接到其他传送带 */
    public boolean autoConnectConveyors = true;

    public ProximaDistributionBlock(String name) {
        super(name);
        
        // 默认配置
        group = BlockGroup.transportation;
        update = true;
        solid = false;
        underBullets = true;
        hasItems = canStoreItems;
        itemCapacity = storageItemCapacity;
        conveyorPlacement = isConveyor;
        unloadable = false;
        noUpdateDisabled = true;
    }

    @Override
    public void init() {
        super.init();
        // 确保hasItems与canStoreItems同步
        hasItems = canStoreItems;
        itemCapacity = canStoreItems ? storageItemCapacity : 0;
    }

    @Override
    public void setStats() {
        super.setStats();
        
        if (isConveyor) {
            stats.add(Stat.itemsMoved, conveyorSpeed, StatUnit.itemsSecond);
        }
        
        if (canStoreItems) {
            stats.add(Stat.itemCapacity, storageItemCapacity);
        }
    }

    /**
     * 检查是否可以从指定方块接收物品
     * @param source 来源方块
     * @return 是否允许输入
     */
    public boolean canAcceptFrom(Building source) {
        if (source == null) return false;
        
        boolean sameClass = source.block.getClass().isAssignableFrom(this.getClass()) ||
                           this.getClass().isAssignableFrom(source.block.getClass());
        
        if (sameClass) {
            return sameClassInput;
        } else {
            return differentClassInput;
        }
    }

    /**
     * 检查是否可以向指定方块输出物品
     * @param target 目标方块
     * @return 是否允许输出
     */
    public boolean canOutputTo(Building target) {
        if (target == null) return false;
        
        boolean sameClass = target.block.getClass().isAssignableFrom(this.getClass()) ||
                           this.getClass().isAssignableFrom(target.block.getClass());
        
        if (sameClass) {
            return sameClassOutput;
        } else {
            return differentClassOutput;
        }
    }

    /**
     * 检查方块是否可以接收物品
     * @param receiver 接收方建筑
     * @param source 来源建筑
     * @param item 物品类型
     * @return 是否可以接收
     */
    public boolean canReceiveItem(Building receiver, Building source, Item item) {
        if (!canStoreItems && !isConveyor) return false;
        if (!canAcceptFrom(source)) return false;
        if (isStorageBlock && receiver != null && receiver.items != null) {
            return receiver.items.get(item) < storageItemCapacity;
        }
        return true;
    }

    /**
     * 获取方块的有效物品容量
     */
    public int getEffectiveItemCapacity() {
        return canStoreItems ? storageItemCapacity : 0;
    }

    /**
     * 物流方块的Build类
     */
    public class ProximaDistributionBuild extends Building {
        
        @Override
        public boolean acceptItem(Building source, Item item) {
            if (source.team != team) return false;
            return ((ProximaDistributionBlock) block).canReceiveItem(this, source, item);
        }

        @Override
        public boolean canDump(Building to, Item item) {
            return ((ProximaDistributionBlock) block).canOutputTo(to);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return ((ProximaDistributionBlock) block).getEffectiveItemCapacity() - items.get(item);
        }
    }
}
