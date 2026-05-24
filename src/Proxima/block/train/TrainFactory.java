package Proxima.block.train;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import Proxima.annotations.Annotations.*;
import arc.util.Eachable;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import Proxima.content.ProximaUnitTypes;

import static mindustry.Vars.*;

public class TrainFactory extends Block {
    public @Load(value = "@-top", fallback = "block-proxima-core-thruster1") TextureRegion topRegion;
    public @Load(value = "@-base", fallback = "block-proxima-core-thruster2") TextureRegion baseRegion;
    
    public float buildTime = 300f;
    public float energyCost = 1000f;
    
    public TrainFactory(String name){
        super(name);
        update = true;
        solid = true;
        rotate = true;
        hasPower = true;
        group = BlockGroup.units;
        sync = true;
        envEnabled |= Env.space | Env.underwater;
    }
    
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.buildTime, buildTime / 60f, StatUnit.seconds);
        stats.add(Stat.powerUse, energyCost / buildTime * 60f, StatUnit.powerSecond);
    }
    
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(baseRegion != null ? baseRegion : Core.atlas.find("block-proxima-core-thruster2"), plan.drawx(), plan.drawy(), tilesize, tilesize, plan.rotation * 90);
    }
    
    public class TrainFactoryBuild extends Building {
        public float progress;
        public boolean building = false;
        public float warmup;
        
        @Override
        public void draw(){
            Draw.rect(baseRegion != null ? baseRegion : Core.atlas.find("block-proxima-core-thruster2"), x, y, tilesize, tilesize, rotation * 90);
            
            Draw.z(Layer.blockOver);
            Draw.rect(topRegion != null ? topRegion : Core.atlas.find("block-proxima-core-thruster1"), x, y, tilesize, tilesize, rotation * 90 + Time.time * 2f);
            
            if(building){
                Draw.color(Pal.accent);
                Draw.alpha(0.5f + Mathf.absin(Time.time, 1f, 0.5f));
                Draw.rect(topRegion != null ? topRegion : Core.atlas.find("block-proxima-core-thruster1"), x, y, tilesize, tilesize, rotation * 90);
                Draw.reset();
            }
        }
        
        @Override
        public void updateTile(){
            if(!enabled) return;
            
            if(building){
                if(power.status >= 0.9f){
                    progress += edelta() / buildTime;
                    warmup = Mathf.approach(warmup, 1f, 0.02f);
                    
                    if(progress >= 1f){
                        spawnTrain();
                        progress = 0f;
                        building = false;
                    }
                }else{
                    warmup = Mathf.approach(warmup, 0f, 0.02f);
                }
            }else{
                warmup = Mathf.approach(warmup, 0f, 0.02f);
            }
        }
        
        public void spawnTrain(){
            float tx = x + Geometry.d4x(rotation) * tilesize;
            float ty = y + Geometry.d4y(rotation) * tilesize;
            
            UnitType type = ProximaUnitTypes.proximaTrain;
            Unit entity = type.create(team);
            entity.set(tx, ty);
            entity.rotation = rotation * 90f;
            
            Groups.unit.add(entity);
            
            TrainSystem.addTrain(entity);
            
            Fx.spawn.at(tx, ty);
        }
        
        @Override
        public void onControlSelect(Unit player){
            if(!building){
                building = true;
                progress = 0f;
            }
        }
        
        @Override
        public boolean canControlSelect(Unit unit){
            return !building && team == unit.team;
        }
        
        @Override
        public void write(arc.util.io.Writes write){
            super.write(write);
            write.f(progress);
            write.b((byte)(building ? 1 : 0));
        }
        
        @Override
        public void read(arc.util.io.Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
            building = read.b() == 1;
        }
    }
}