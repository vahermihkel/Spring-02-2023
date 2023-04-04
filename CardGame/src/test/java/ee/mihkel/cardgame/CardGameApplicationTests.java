package ee.mihkel.cardgame;

import ee.mihkel.cardgame.game.Card;
import ee.mihkel.cardgame.game.GameService;
import ee.mihkel.cardgame.game.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CardGameApplicationTests {

    @Autowired
    GameService gameService;

    @Test
    void test_if_one_of_the_suits_when_starting_the_game() {
        Card card = gameService.getCard();
        List<Suit> suits = new ArrayList<>(Arrays.asList(Suit.CLUB, Suit.HEART, Suit.SPADE, Suit.DIAMOND));
        boolean isSuit = suits.contains(card.getSuit());
        assertTrue(isSuit);
    }

    @Test
    void test_if_one_value_between_2_and_10_when_getting_card() {
        Card card = gameService.getCard();
        boolean isSuit = card.getValue() >= 2 && card.getValue() <= 10;
        assertTrue(isSuit);
    }

    @Test
    void test_if_returns_slow_when_waiting_10_seconds() throws InterruptedException {
        gameService.savePlayer("Mihkel");
        gameService.getCard();
        Thread.sleep(10001);
        String response = gameService.userGuess("lower");
        assertEquals("slow", response);
    }

    @Test
    void test_if_returns_wrong_when_inserting_random_value() {
        gameService.savePlayer("Mihkel");
        gameService.getCard();
        String response = gameService.userGuess("random_value");
        assertEquals("wrong", response);
    }

    @Test
    void test_if_returns_wrong_when_inserting_random_value_two_times() {
        gameService.savePlayer("Mihkel");
        gameService.getCard();
        gameService.userGuess("random_value");
        String response = gameService.userGuess("random_value");
        assertEquals("wrong", response);
    }

    @Test
    void test_if_returns_over_when_inserting_random_value_three_times() {
        gameService.savePlayer("Mihkel");
        gameService.getCard();
        gameService.userGuess("random_value");
        gameService.userGuess("random_value");
        String response = gameService.userGuess("random_value");
        assertEquals("over", response);
    }
}
