package Proxima.expand.util;

import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.Seq;

public class NHFunc {
    public static final Rand rand = new Rand(0);

    public static <T> void shuffle(Seq<T> seq, Rand random) {
        for (int i = seq.size - 1; i > 0; i--) {
            int j = random.random(i);
            T temp = seq.get(i);
            seq.set(i, seq.get(j));
            seq.set(j, temp);
        }
    }

    public static Rand rand(long seed) {
        rand.setSeed(seed);
        return rand;
    }
}