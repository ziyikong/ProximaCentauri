package Proxima.block;

/**
 * 示例传送带类
 * 展示如何继承 ProximaDistributionBlock 并修改配置
 */
public class ExampleConveyor extends ProximaDistributionBlock {

    public ExampleConveyor(String name) {
        super(name);
        
        // 修改父类配置
        isConveyor = true;
        conveyorSpeed = 15f;
        autoConnectConveyors = true;
        
        // 只允许同类方块输入输出（纯粹的传送带网络）
        sameClassInput = true;
        sameClassOutput = true;
        differentClassInput = false;
        differentClassOutput = true;  // 可以输出到非传送带方块
        
        // 传送带不需要存储
        canStoreItems = false;
        isStorageBlock = false;
    }

    public class ExampleConveyorBuild extends ProximaDistributionBuild {
        // 自定义逻辑可以在这里添加
    }
}
