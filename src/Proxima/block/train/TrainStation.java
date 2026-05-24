package Proxima.block.train;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import Proxima.annotations.Annotations.*;
import arc.util.Eachable;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;
import Proxima.content.ProximaUnitTypes;

import static mindustry.Vars.*;

public class TrainStation extends Block {
    public @Load(value = "@-top", fallback = "block-proxima-core-thruster1") TextureRegion topRegion;
    public @Load(value = "@-side", fallback = "block-proxima-core-thruster2") TextureRegion sideRegion;
    
    public float loadTime = 60f;
    public int itemCapacity = 100;
    public float range = 2f;
    
    public TrainStation(String name){
        super(name);
        update = true;
        solid = true;
        rotate = true;
        hasItems = true;
        group = BlockGroup.transportation;
        sync = true;
        envEnabled |= Env.space | Env.underwater;
    }
    
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.itemCapacity, itemCapacity);
    }
    
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(topRegion != null ? topRegion : Core.atlas.find("block-proxima-core-thruster1"), plan.drawx(), plan.drawy(), tilesize, tilesize, plan.rotation * 90);
    }
    
    public class TrainStationBuild extends Building {
        public float progress;
        public boolean loading = false;
        public Unit train;
        
        @Override
        public void draw(){
            Draw.rect(topRegion != null ? topRegion : Core.atlas.find("block-proxima-core-thruster1"), x, y, tilesize, tilesize, rotation * 90);
            
            Draw.z(Layer.blockOver);
            if(loading && train != null && !train.dead){
                Draw.color(Pal.accent);
                Lines.line(x, y, train.x, train.y);
                Draw.reset();
            }
        }
        
        @Override
        public void updateTile(){
            if(!enabled) return;
            
            if(train != null && !train.dead){
                float dst = train.dst(x, y);
                if(dst > range * tilesize){
                    train = null;
                    loading = false;
                }else{
                    if(items.total() > 0 && train.type.itemCapacity > 0){
                        loading = true;
                        progress += edelta() / loadTime;
                        
                        if(progress >= 1f){
                            progress = 0f;
                        }
                    }else{
                        loading = false;
                        progress = 0f;
                    }
                }
            }else{
                loading = false;
                progress = 0f;
                train = null;
                
                Units.nearby(team, x, y, range * tilesize, unit -> {
                    if(unit.type == ProximaUnitTypes.proximaTrain && train == null){
                        train = unit;
                    }
                });
            }
        }
        
        @Override
        public boolean acceptItem(Building source, Item item){
            return items.total() < itemCapacity;
        }
        
        @Override
        public void handleItem(Building source, Item item){
            items.add(item, 1);
        }
        
        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return Math.min(amount, itemCapacity - items.total());
        }
        
        @Override
        public void handleStack(Item item, int amount, Teamc source){
            items.add(item, Math.min(amount, itemCapacity - items.total()));
        }
        
        @Override
        public void write(arc.util.io.Writes write){
            super.write(write);
            write.f(progress);
            write.b((byte)(loading ? 1 : 0));
        }
        
        @Override
        public void read(arc.util.io.Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
            loading = read.b() == 1;
        }
    }
}