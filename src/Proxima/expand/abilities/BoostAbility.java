package Proxima.expand.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.Interval;
import mindustry.Vars;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Trail;
import mindustry.input.Binding;
import mindustry.type.UnitType;
import Proxima.expand.units.AncientEngine;

public class BoostAbility extends Ability {
    public static final int maxSize = 8;

    public boolean drawAirFlow = true;

    public float angleMaxDst = 90f;
    public float velocityMultiple;
    public float warmupTime = 120f;
    public int trailLength = 8;

    public float angleCone = 5f;
    protected Seq<Trail> trails;
    protected Seq<AncientEngine> ancientEngines;
    protected Queue<Float> seq = new Queue<>(maxSize + 1);
    protected Interval timer = new Interval();

    public BoostAbility() {}

    public BoostAbility(boolean drawAirFlow, float velocityMultiple, float angleCone) {
        this.drawAirFlow = drawAirFlow;
        this.velocityMultiple = velocityMultiple;
        this.angleCone = angleCone;
    }

    public BoostAbility(float velocityMultiple, float angleCone) {
        this.velocityMultiple = velocityMultiple;
        this.angleCone = angleCone;
    }

    public BoostAbility(float velocityMultiple) {
        this.velocityMultiple = velocityMultiple;
    }

    @Override
    public void init(UnitType type) {
        trails = new Seq<>();
        ancientEngines = new Seq<>();
        float size = type.engineSize;
        for (UnitType.UnitEngine e : type.engines) {
            if (e instanceof AncientEngine ae) {
                ancientEngines.add(ae);
            } else {
                int f = (int)(Mathf.clamp(e.radius / size) * trailLength);
                trails.add(new Trail(f));
            }
        }
    }

    @Override
    public BoostAbility copy() {
        BoostAbility out = (BoostAbility)super.copy();

        out.trails = new Seq<>(trails.size);
        for (Trail trail : trails) {
            out.trails.add(new Trail(trail.length));
        }

        out.seq = new Queue<>(maxSize + 1);
        out.timer = new Interval();
        return out;
    }

    public void resetTrails() {
        if (trails != null) {
            for (Trail trail : trails) {
                if (trail != null) {
                    trail.clear();
                }
            }
        }
        seq.clear();
        timer = new Interval();
    }

    public float warmup(float angle) {
        float f = 0;
        for (float i : seq) if (Angles.within(angle, i, angleCone)) f++;
        return f / seq.size;
    }

    public boolean allSame(float angle, float lookAng) {
        if (seq.size < maxSize - 1 || !Angles.within(angle, lookAng, angleMaxDst)) return false;
        for (float f : seq) {
            if (!Angles.within(angle, f, angleCone)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(Unit unit) {
        float angle = unit.vel.angle();
        float speed = unit.vel.len();
        boolean same = allSame(angle, unit.rotation());
        boolean shiftBoost = Core.input.keyDown(Binding.boost);
        boolean boosting = same || shiftBoost;

        if (speed < 0.01f && !seq.isEmpty()) {
            seq.removeFirst();
        }

        if (boosting) {
            unit.speedMultiplier(unit.speedMultiplier() * velocityMultiple);
        }

        if (seq.size > maxSize) seq.removeFirst();
        if (timer.get(12f) && speed > 0.1f) seq.add(angle);

        for (AncientEngine ae : ancientEngines) {
            ae.setBoost(boosting ? velocityMultiple : 1f);
        }

        if (Vars.headless) return;

        for (int i = 0; i < trails.size; i++) {
            Trail trail = trails.get(i);
            if (i >= unit.type.engines.size) continue;
            UnitType.UnitEngine engine = unit.type.engines.get(i);
            if (engine instanceof AncientEngine) continue;

            Vec2 vec2 = unitEngineOffset(unit, engine);
            trail.update(unit.x + vec2.x, unit.y + vec2.y, boosting ? 1 : 0);
        }
    }

    @Override
    public void draw(Unit unit) {
        float z = Draw.z();
        Draw.z(unit.type.engineLayer > 0 ? unit.type.engineLayer : unit.type.lowAltitude ? Layer.flyingUnitLow - 0.001f : Layer.flyingUnit - 0.001f);
        Color color = unit.type.engineColor == null ? unit.team.color : unit.type.engineColor;
        for (int i = 0; i < trails.size; i++) {
            if (i >= unit.type.engines.size) continue;
            UnitType.UnitEngine engine = unit.type.engines.get(i);
            if (engine instanceof AncientEngine) continue;
            trails.get(i).draw(color, engine.radius / 1.25f);
        }
        Draw.z(z);
    }

    private static final Vec2 tmp = new Vec2();

    private Vec2 unitEngineOffset(Unit unit, UnitType.UnitEngine engine) {
        tmp.trns(unit.rotation, engine.y, -engine.x);
        return tmp;
    }
}