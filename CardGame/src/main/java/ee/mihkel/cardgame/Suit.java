package ee.mihkel.cardgame;

import java.util.Random;

public enum Suit {
    HEART, DIAMOND, SPADE, CLUB;

    public static Suit randomSuit() {
        Random rand = new Random();
        return values()[rand.nextInt(values().length)];
    }
}
