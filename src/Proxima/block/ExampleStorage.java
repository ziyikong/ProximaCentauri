package Proxima.block;

/**
 * 示例存储方块类
 * 展示纯粹存储方块的配置方式
 */
public class ExampleStorage extends ProximaDistributionBlock {

    public ExampleStorage(String name) {
        super(name);
        
        // 纯粹的存储方块配置
        isStorageBlock = true;
        canStoreItems = true;
        storageItemCapacity = 100;
        
        // 允许从任何类型方块输入
        sameClassInput = true;
        differentClassInput = true;
        
        // 不主动输出（由其他方块提取）
        sameClassOutput = false;
        differentClassOutput = false;
        
        // 不是传送带
        isConveyor = false;
        
        // 存储方块通常是固体的
        solid = true;
    }

    public class ExampleStorageBuild extends ProximaDistributionBuild {
        // 自定义存储逻辑可以在这里添加
    }
}
