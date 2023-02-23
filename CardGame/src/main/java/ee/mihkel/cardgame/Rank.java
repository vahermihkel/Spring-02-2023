package ee.mihkel.cardgame;

import java.util.Random;

public enum Rank {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGTH, NINE, TEN, JACK, QUEEN, KING, ACE;

    public static Rank randomRank() {
        Random rand = new Random();
        return values()[rand.nextInt(values().length)];
    }
}
