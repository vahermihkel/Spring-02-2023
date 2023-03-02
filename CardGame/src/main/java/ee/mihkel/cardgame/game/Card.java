package ee.mihkel.cardgame.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private Suit suit;
    private Rank rank;
    private int value;

    public Card() {
//        Suit suit = new Suit(); !enumiga ei saa
//        suit.randomSuit(); !enumiga ei saa
        this.suit = Suit.randomSuit();
        this.rank = Rank.randomRank();
        this.value = determineValue();
    }

    private int determineValue() {
        return switch (rank) {
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGTH -> 8;
            case NINE -> 9;
            default -> 10;
        };
    }

//    public String randomSuit() {
//        String[] suits = {"HEART","DIAMOND","SPADE", "CLUB"};
////        int randomIndex = (int) (Math.random() * suits.length);
//        Random rand = new Random();
//        return suits[rand.nextInt(suits.length)];
//    }
}
