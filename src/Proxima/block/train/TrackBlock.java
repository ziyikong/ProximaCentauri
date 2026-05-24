package Proxima.block.train;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import Proxima.annotations.Annotations.*;
import mindustry.content.*;
import mindustry.entities.units.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class TrackBlock extends Block {
    public @Load(value = "@-straight", fallback = "block-proxima-core-thruster1") TextureRegion straightRegion;
    public @Load(value = "@-curve", fallback = "block-proxima-core-thruster1") TextureRegion curveRegion;
    public @Load(value = "@-junction", fallback = "block-proxima-core-thruster1") TextureRegion junctionRegion;
    
    public float speed = 4f;
    
    public TrackBlock(String name){
        super(name);
        update = true;
        solid = false;
        rotate = false;
        group = BlockGroup.transportation;
        placeableOn = true;
        underBullets = true;
        saveData = true;
    }
    
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(straightRegion != null ? straightRegion : Core.atlas.find("block-proxima-core-thruster1"), plan.drawx(), plan.drawy());
    }
    
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        
        Tile tile = world.tile(x, y);
        if(tile == null) return;
        
        TrackBuild build = (TrackBuild) tile.build;
        if(build != null){
            Draw.color(Pal.accent);
            for(int dir : build.shape.connections){
                if(dir >= 0){
                    float dx = Geometry.d4x(dir) * tilesize;
                    float dy = Geometry.d4y(dir) * tilesize;
                    Lines.line(x * tilesize + tilesize/2f, y * tilesize + tilesize/2f,
                              x * tilesize + tilesize/2f + dx, y * tilesize + tilesize/2f + dy);
                }
            }
            Draw.reset();
        }
    }
    
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return tile.floor().solid || super.canPlaceOn(tile, team, rotation);
    }
    
    public class TrackBuild extends Building {
        public TrackShape shape = TrackShape.none;
        public Seq<TrackBuild> neighbors = new Seq<>();
        
        @Override
        public void draw(){
            TextureRegion region = getRegionForShape();
            float rot = getRotationForShape();
            
            Draw.z(Layer.block - 0.1f);
            Draw.rect(region != null ? region : Core.atlas.find("block-proxima-core-thruster1"), x, y, tilesize, tilesize, rot);
        }
        
        private TextureRegion getRegionForShape(){
            if(shape.isCurve()){
                return curveRegion != null ? curveRegion : Core.atlas.find("block-proxima-core-thruster1");
            }else if(shape.isJunction()){
                return junctionRegion != null ? junctionRegion : Core.atlas.find("block-proxima-core-thruster1");
            }
            return straightRegion != null ? straightRegion : Core.atlas.find("block-proxima-core-thruster1");
        }
        
        private float getRotationForShape(){
            switch(shape){
                case straightX: return 0f;
                case straightY: return 90f;
                case curveNE: return 0f;
                case curveNW: return 90f;
                case curveSW: return 180f;
                case curveSE: return 270f;
                default: return 0f;
            }
        }
        
        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            updateConnections();
        }
        
        public void updateConnections(){
            neighbors.clear();
            
            for(int i = 0; i < 4; i++){
                Tile neighbor = tile.nearby(Geometry.d4x(i), Geometry.d4y(i));
                if(neighbor != null && neighbor.build instanceof TrackBuild){
                    neighbors.add((TrackBuild) neighbor.build);
                }
            }
            
            updateShape();
        }
        
        public void updateShape(){
            if(neighbors.size == 0){
                shape = TrackShape.none;
                return;
            }
            
            if(neighbors.size >= 3){
                shape = TrackShape.junction;
                return;
            }
            
            if(neighbors.size == 1){
                Tile neighbor = neighbors.first().tile;
                int dir = tile.relativeTo(neighbor);
                shape = TrackShape.fromDirections(dir, -1);
                return;
            }
            
            if(neighbors.size == 2){
                Tile n1 = neighbors.get(0).tile;
                Tile n2 = neighbors.get(1).tile;
                
                int dir1 = tile.relativeTo(n1);
                int dir2 = tile.relativeTo(n2);
                
                shape = TrackShape.fromDirections(dir1, dir2);
            }
        }
        
        public boolean hasConnection(int direction){
            return shape.connects(direction);
        }
        
        public TrackBuild getNeighbor(int direction){
            for(TrackBuild neighbor : neighbors){
                if(tile.relativeTo(neighbor.tile) == direction){
                    return neighbor;
                }
            }
            return null;
        }
        
        @Override
        public byte version(){
            return 1;
        }
        
        @Override
        public void write(arc.util.io.Writes write){
            super.write(write);
            write.b((byte) shape.ordinal());
        }
        
        @Override
        public void read(arc.util.io.Reads read, byte revision){
            super.read(read, revision);
            shape = TrackShape.values()[read.b()];
        }
    }
}