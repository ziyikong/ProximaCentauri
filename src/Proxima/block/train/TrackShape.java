package Proxima.block.train;

import arc.math.geom.*;

public enum TrackShape {
    none(-1, -1),
    
    straight(0, 2),
    straightX(0, 2),
    straightY(1, 3),
    
    curveNE(0, 1),
    curveNW(1, 2),
    curveSW(2, 3),
    curveSE(3, 0),
    
    junction(0, 1, 2, 3),
    
    rampUp(0, -1),
    rampDown(2, -1);

    public final int[] connections;
    
    TrackShape(int... connections){
        this.connections = connections;
    }
    
    public boolean connects(int direction){
        for(int d : connections){
            if(d == direction) return true;
        }
        return false;
    }
    
    public boolean isCurve(){
        return this == curveNE || this == curveNW || this == curveSW || this == curveSE;
    }
    
    public boolean isStraight(){
        return this == straight || this == straightX || this == straightY;
    }
    
    public boolean isJunction(){
        return this == junction;
    }
    
    public static TrackShape fromDirections(int dir1, int dir2){
        if(dir1 == -1 || dir2 == -1) return none;
        
        if((dir1 == 0 && dir2 == 2) || (dir1 == 2 && dir2 == 0)){
            return straightX;
        }
        if((dir1 == 1 && dir2 == 3) || (dir1 == 3 && dir2 == 1)){
            return straightY;
        }
        if((dir1 == 0 && dir2 == 1) || (dir1 == 1 && dir2 == 0)){
            return curveNE;
        }
        if((dir1 == 1 && dir2 == 2) || (dir1 == 2 && dir2 == 1)){
            return curveNW;
        }
        if((dir1 == 2 && dir2 == 3) || (dir1 == 3 && dir2 == 2)){
            return curveSW;
        }
        if((dir1 == 3 && dir2 == 0) || (dir1 == 0 && dir2 == 3)){
            return curveSE;
        }
        
        return none;
    }
    
    public Vec2 getDirectionVector(int direction){
        return new Vec2(Geometry.d4x(direction), Geometry.d4y(direction));
    }
}