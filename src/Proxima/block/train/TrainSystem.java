package Proxima.block.train;

import arc.struct.*;
import mindustry.gen.*;
import mindustry.world.*;

public class TrainSystem {
    private static final Seq<Unit> trains = new Seq<>();
    
    public static void addTrain(Unit train){
        trains.add(train);
    }
    
    public static void removeTrain(Unit train){
        trains.remove(train);
    }
    
    public static Seq<Unit> getTrains(){
        return trains;
    }
    
    public static Unit findNearestTrain(float x, float y, float range){
        Unit nearest = null;
        float nearestDist = range;
        
        for(Unit train : trains){
            float dist = train.dst(x, y);
            if(dist < nearestDist){
                nearestDist = dist;
                nearest = train;
            }
        }
        
        return nearest;
    }
    
    public static Seq<Unit> getTrainsOnTrack(TrackBlock.TrackBuild track){
        Seq<Unit> result = new Seq<>();
        
        for(Unit train : trains){
            if(train.tileOn() == track.tile){
                result.add(train);
            }
        }
        
        return result;
    }
    
    public static void update(){
        trains.each(train -> {
            if(!train.dead){
                // train update logic handled by unit system
            }else{
                removeTrain(train);
            }
        });
    }
}