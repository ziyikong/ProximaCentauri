# ProximaCentauriMod 项目结构统计

## 项目概述

这是一个 Mindustry 游戏的 Java 模组项目，名为 **ProximaCentauriMod**（比邻星模组）。项目包含多个功能模块，涵盖方块、物品、液体、单位、特效等内容。

---

## 目录结构

```
src/Proxima/
├── annotations/          # 注解定义
├── audio/                # 音频系统
├── block/                # 方块系统（核心模块）
│   └── train/            # 铁路子系统
├── content/              # 内容注册
├── effects/              # 特效系统
├── entities/             # 实体系统
├── expand/               # 扩展功能
│   ├── abilities/        # 单位能力
│   ├── bullets/          # 子弹类型
│   ├── units/            # 单位扩展
│   └── util/             # 扩展工具
├── graphics/             # 图形渲染
├── input/                # 输入处理
├── items/                # 物品系统
├── liquids/              # 液体系统
├── maps/                 # 地图生成
├── special/              # 特殊内容
└── util/                 # 通用工具
```

---

## 类统计

### 按模块分类

| 模块 | 文件数 | 主要内容 |
|------|--------|----------|
| **核心** | 5 | 模组入口、颜色、音效、工具 |
| **block** | 35 | 方块定义（含RBMK、DFC、铁路等） |
| **content** | 3 | 行星、科技树、单位类型 |
| **items** | 3 | 物品定义 |
| **liquids** | 1 | 液体定义 |
| **effects** | 10 | 特效系统 |
| **graphics** | 14 | 图形渲染工具 |
| **expand** | 6 | 扩展功能 |
| **special** | 3 | 特殊内容 |
| **input** | 2 | 输入处理 |
| **audio** | 2 | 音频系统 |
| **maps** | 2 | 地图生成 |
| **entities** | 9 | 实体定义 |
| **util** | 1 | 工具类 |
| **annotations** | 1 | 注解 |
| **总计** | **96** | |

---

## 核心类关系图

### 模块依赖关系

```
ProximaCentauriMod (入口)
    ├── SpecialDeathEffects.load()
    ├── SpecialContent.load()
    ├── ProximaItems.load()
    ├── ProximaLiquids.load()
    ├── ProximaUnitTypes.load()
    ├── ProximaBlocks.load()
    ├── ProximaPlanets.load()
    └── ProximaTechTree.load()
```

### 方块继承体系

```
mindustry.world.Block (Mindustry基类)
    ├── RBMKBase (RBMK反应堆基础)
    │   ├── RBMKRod (燃料棒)
    │   ├── RBMKControl (控制棒)
    │   ├── RBMKCooler (冷却器)
    │   ├── RBMKReflector (反射器)
    │   ├── RBMKModerator (慢化剂)
    │   ├── RBMKAbsorber (吸收器)
    │   ├── RBMKBlank (空白结构)
    │   ├── RBMKBoiler (锅炉)
    │   ├── RBMKHeater (加热器)
    │   ├── RBMKConsole (控制台)
    │   └── RBMKPusher (推送器)
    │
    ├── DFCBase (DFC系统基础)
    │   ├── DFCore (DFC核心)
    │   ├── DFCLaserEmitter (激光发射器)
    │   ├── DFCStabilizer (稳定器)
    │   ├── DFCInjector (燃料注入器)
    │   ├── DFCEnergyEmitter (能量发射器)
    │   ├── DFCEnergyReceiver (能量接收器)
    │   ├── DFCExchanger (交换器)
    │   └── DFCCreativeEmitter (创意发射器)
    │
    ├── ProximaCoreBlock (比邻星核心)
    ├── LargeHeatPipe (大型热管)
    ├── LargeSteamTurbine (大型蒸汽轮机)
    ├── LaserEmitter / LaserReceiver / LaserMirror (激光系统)
    ├── MechanicalArm / RoboticArmBase (机械臂)
    ├── RockCoreDrill (岩芯钻机)
    ├── RotatableBlock / RotatableCrafter (可旋转方块)
    ├── SixteenDirectionBlock (16方向方块)
    ├── Train相关 (铁路系统)
    └── 其他功能性方块
```

### RBMK反应堆系统组成

| 组件 | 作用 |
|------|------|
| `RBMKBase` | 反应堆底座，提供液体存储和中子通量传导 |
| `RBMKRod` | 燃料棒，产生中子和热量 |
| `RBMKControl` | 控制棒，调节反应强度 |
| `RBMKCooler` | 冷却器，降低温度 |
| `RBMKReflector` | 反射器，反射中子 |
| `RBMKModerator` | 慢化剂，减慢快中子 |
| `RBMKAbsorber` | 吸收器，吸收多余中子 |
| `RBMKBoiler` | 锅炉，将水转化为蒸汽 |
| `RBMKHeater` | 加热器，提升温度 |
| `RBMKConsole` | 控制台，显示反应堆状态 |

### DFC系统组成

| 组件 | 作用 |
|------|------|
| `DFCBase` | DFC系统基础类 |
| `DFCore` | DFC核心，能量产生中心 |
| `DFCLaserEmitter` | 激光发射器 |
| `DFCStabilizer` | 稳定器，维持系统稳定 |
| `DFCInjector` | 燃料注入器 |
| `DFCEnergyEmitter` | 能量发射器 |
| `DFCEnergyReceiver` | 能量接收器 |
| `DFCExchanger` | 交换器，能量交换 |

### 铁路系统组成

```
TrackBlock (铁轨)
    └── TrackShape (铁轨形状计算)
TrainFactory (列车工厂)
TrainStation (火车站台)
TrainSystem (列车控制系统)
```

---

## 内容模块关系

### 物品与方块的依赖

```
ProximaItems (物品)
    ├── iron, uranium, manganese, quartz (矿石)
    ├── plutonium238BerylliumSource (中子源)
    ├── heu235UraniumFuel (HEU燃料)
    └── RBMKRodItem系列 (RBMK燃料棒)
            └── 用于 RBMKRod 方块

ProximaLiquids (液体)
    ├── steam (蒸汽)
    ├── dfcCoolant (DFC冷却液)
    ├── dfcFuel (DFC燃料)
    ├── dfcAntimatter (反物质)
    └── dfcPositiveMatter (正物质)
```

### 单位系统

```
ProximaUnitTypes
    ├── proximaCoreMech (比邻星核心机甲)
    │   ├── 使用 AncientEngine (引擎)
    │   ├── 使用 BoostAbility (能力)
    │   └── 使用 DelayedPointBulletType / ChainBulletType (子弹)
    └── proximaTrain (列车单位)
```

---

## 特效与图形系统

### 特效模块

```
ProximaFX (主要特效定义)
    ├── shield (护盾)
    ├── aoeExplosion2 (范围爆炸)
    ├── apathy系列 (冷漠特效)
    ├── empathy系列 (共情特效)
    ├── end系列 (末日特效)
    ├── des系列 (毁灭特效)
    └── 其他特效...

SpecialDeathEffects (特殊死亡效果)
    ├── Carve (切割)
    ├── Devastation (毁灭)
    ├── Disintegration (瓦解)
    ├── Fragmentation (碎裂)
    └── Severation (切断)
```

### 图形模块

```
graphics/
    ├── AsteroidBeltMesh (小行星带网格)
    ├── ZAxisSkyMesh (Z轴天空网格)
    ├── ProximaShaders (着色器)
    ├── GraphicUtils (图形工具)
    ├── 各种Batch渲染器
    └── 3D相关工具
```

---

## 扩展功能模块

```
expand/
    ├── abilities/
    │   ├── AttackSlowAbility (攻击减速)
    │   └── BoostAbility (助推)
    ├── bullets/
    │   ├── AccelBulletType (加速子弹)
    │   ├── ChainBulletType (链式子弹)
    │   └── DelayedPointBulletType (延迟定点子弹)
    ├── units/
    │   └── AncientEngine (古代引擎)
    └── util/
        ├── NHFunc (数学函数)
        └── PosLightning (位置闪电)
```

---

## 特殊内容模块

```
special/
    ├── SpecialContent (特殊内容注册)
    ├── SpecialMain (特殊主逻辑)
    └── SpecialState (特殊状态管理)
```

---

## 关键数据流

### 游戏启动流程

```
ProximaCentauriMod构造函数
    └── Events.on(ClientLoadEvent) → 替换输入处理器

ProximaCentauriMod.loadContent()
    ├── SpecialDeathEffects.load()
    ├── SpecialContent.load()
    ├── ProximaItems.load()
    ├── ProximaLiquids.load()
    ├── ProximaUnitTypes.load()
    ├── ProximaBlocks.load()
    ├── ProximaPlanets.load()
    └── ProximaTechTree.load()
```

### RBMK反应堆工作流程

```
燃料棒(RBMKRod)产生中子
    ├── 中子与慢化剂(RBMKModerator)相互作用 → 快中子变慢中子
    ├── 慢中子被反射器(RBMKReflector)反射
    ├── 多余中子被吸收器(RBMKAbsorber)吸收
    ├── 控制棒(RBMKControl)调节反应强度
    ├── 冷却器(RBMKCooler)带走热量
    ├── 锅炉(RBMKBoiler)产生蒸汽
    └── 加热器(RBMKHeater)提升温度
```

---

## 总结

本项目是一个功能丰富的 Mindustry 模组，主要特点：

1. **核心系统**：RBMK反应堆系统、DFC能量系统
2. **特色功能**：铁路运输系统、16方向方块、激光系统
3. **视觉效果**：丰富的特效系统、自定义图形渲染
4. **扩展能力**：单位能力系统、自定义子弹类型

项目结构清晰，模块化程度高，便于维护和扩展。