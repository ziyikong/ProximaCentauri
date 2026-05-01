package Proxima.content;

import arc.func.Cons;
import arc.func.Prov;
import arc.graphics.Color;
import arc.scene.style.Drawable;
import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives.Objective;
import mindustry.type.ItemStack;
import mindustry.type.Planet;
import Proxima.block.ProximaBlocks;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

/**
 * 比邻星科技树类
 * 可自定义科技树链接效果
 */
public class ProximaTechTree {

    /** 科技树链接效果配置 */
    public static TechTreeLinkEffect linkEffect = new TechTreeLinkEffect();

    /** 加载科技树 */
    public static void load() {
        // 为比邻星星球设置科技树
        // 以比邻星核心作为科技树的根节点
        ProximaPlanets.proxima.techTree = nodeRoot("proxima", ProximaBlocks.proximaCore, () -> {
            // 科技树内容将在未来添加
        });
    }

    /**
     * 科技树链接效果配置类
     */
    public static class TechTreeLinkEffect {

        /** 链接颜色 */
        public Color linkColor = Color.valueOf("87CEEB");
        
        /** 高亮链接颜色 */
        public Color highlightedLinkColor = Color.valueOf("5DADE2");
        
        /** 已研究链接颜色 */
        public Color researchedLinkColor = Color.valueOf("2ECC71");
        
        /** 链接宽度 */
        public float linkWidth = 4f;
        
        /** 链接动画速度 */
        public float linkAnimSpeed = 1f;
        
        /** 是否显示链接脉冲效果 */
        public boolean showPulseEffect = true;
        
        /** 脉冲颜色 */
        public Color pulseColor = Color.valueOf("87CEEB").a(0.5f);
        
        /** 脉冲大小 */
        public float pulseSize = 6f;
        
        /** 脉冲速度 */
        public float pulseSpeed = 0.5f;
        
        /** 链接样式 */
        public LinkStyle linkStyle = LinkStyle.STRAIGHT;
        
        /** 是否显示光晕效果 */
        public boolean showGlowEffect = false;
        
        /** 光晕颜色 */
        public Color glowColor = Color.valueOf("87CEEB").a(0.3f);
        
        /** 光晕半径 */
        public float glowRadius = 15f;
        
        /** 自定义链接渲染器（可选） */
        public Prov<LinkRenderer> customRenderer = null;

        /** 设置链接颜色 */
        public TechTreeLinkEffect setLinkColor(Color color) {
            this.linkColor = color;
            return this;
        }

        /** 设置高亮链接颜色 */
        public TechTreeLinkEffect setHighlightedLinkColor(Color color) {
            this.highlightedLinkColor = color;
            return this;
        }

        /** 设置已研究链接颜色 */
        public TechTreeLinkEffect setResearchedLinkColor(Color color) {
            this.researchedLinkColor = color;
            return this;
        }

        /** 设置链接宽度 */
        public TechTreeLinkEffect setLinkWidth(float width) {
            this.linkWidth = width;
            return this;
        }

        /** 设置链接动画速度 */
        public TechTreeLinkEffect setLinkAnimSpeed(float speed) {
            this.linkAnimSpeed = speed;
            return this;
        }

        /** 设置是否显示脉冲效果 */
        public TechTreeLinkEffect setShowPulseEffect(boolean show) {
            this.showPulseEffect = show;
            return this;
        }

        /** 设置脉冲颜色 */
        public TechTreeLinkEffect setPulseColor(Color color) {
            this.pulseColor = color;
            return this;
        }

        /** 设置脉冲大小 */
        public TechTreeLinkEffect setPulseSize(float size) {
            this.pulseSize = size;
            return this;
        }

        /** 设置脉冲速度 */
        public TechTreeLinkEffect setPulseSpeed(float speed) {
            this.pulseSpeed = speed;
            return this;
        }

        /** 设置链接样式 */
        public TechTreeLinkEffect setLinkStyle(LinkStyle style) {
            this.linkStyle = style;
            return this;
        }

        /** 设置是否显示光晕效果 */
        public TechTreeLinkEffect setShowGlowEffect(boolean show) {
            this.showGlowEffect = show;
            return this;
        }

        /** 设置光晕颜色 */
        public TechTreeLinkEffect setGlowColor(Color color) {
            this.glowColor = color;
            return this;
        }

        /** 设置光晕半径 */
        public TechTreeLinkEffect setGlowRadius(float radius) {
            this.glowRadius = radius;
            return this;
        }

        /** 设置自定义链接渲染器 */
        public TechTreeLinkEffect setCustomRenderer(Prov<LinkRenderer> renderer) {
            this.customRenderer = renderer;
            return this;
        }
    }

    /**
     * 链接样式枚举
     */
    public enum LinkStyle {
        /** 直线样式 */
        STRAIGHT,
        /** 曲线样式 */
        CURVED,
        /** 阶梯样式 */
        STEPPED,
        /** 波浪样式 */
        WAVY
    }

    /**
     * 自定义链接渲染器接口
     */
    public interface LinkRenderer {
        /**
         * 渲染链接
         * @param fromX 起始X坐标
         * @param fromY 起始Y坐标
         * @param toX 结束X坐标
         * @param toY 结束Y坐标
         * @param researched 是否已研究
         * @param highlighted 是否高亮
         * @param effect 链接效果配置
         */
        void render(float fromX, float fromY, float toX, float toY, boolean researched, boolean highlighted, TechTreeLinkEffect effect);
    }

    /**
     * 应用科技树效果到星球
     * @param planet 要应用效果的星球
     */
    public static void applyToPlanet(Planet planet) {
        // 这里可以将链接效果保存到星球中
        // 实际使用时，需要覆盖科技树的渲染逻辑
    }
}
