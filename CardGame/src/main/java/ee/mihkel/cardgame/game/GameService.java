package ee.mihkel.cardgame.game;

import ee.mihkel.cardgame.database.GameRepository;
import ee.mihkel.cardgame.database.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class GameService {

    private Card card;
    private LocalDateTime roundStartTime;
    private int correctAnswers;
    private int lives;
    private long gameStartTime;

    private Player player;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;


    public String savePlayer(String playerName) {
        Optional<Player> playerFound = playerRepository.findById(playerName);
        if (playerFound.isEmpty()) {
            Player newPlayer = new Player(playerName, new Date(), 0);
            player = playerRepository.save(newPlayer);
        } else {
            player = playerFound.get();
        }
        return "OK";
    }

    public Card getCard() {
        roundStartTime = LocalDateTime.now();
        if (card == null) {
            correctAnswers = 0;
            lives = 3;
            gameStartTime = System.currentTimeMillis();
            card = new Card();
        }
        return card;
    }

    public String userGuess(String userGuess) {
        LocalDateTime roundStartTimeLater = roundStartTime.plusSeconds(10);
        LocalDateTime actualTime = LocalDateTime.now();
        if (actualTime.isAfter(roundStartTimeLater)) {
            lives--;
            if (lives == 0) {
                finishAndSaveGame();
                return "Game over!";
            }
            return "Not answered in 10 seconds!";
        }
        Card newCard = new Card();
        String response;
        if (isGuessAndValueComparisonCorrect(userGuess, newCard)) {
            response = "Correct!";
            correctAnswers++;
        } else {
            response = "Wrong!";
            lives--;
            if (lives == 0) {
                finishAndSaveGame();
                return "Game over!";
            }
        }
        card = newCard;
        return response;
    }

    private boolean isGuessAndValueComparisonCorrect(String userGuess, Card newCard) {
        return userGuess.equals("lower") && card.getValue() > newCard.getValue() ||
                userGuess.equals("higher") && card.getValue() < newCard.getValue() ||
                userGuess.equals("equal") && card.getValue() == newCard.getValue();
    }

    private void finishAndSaveGame() {
        card = null;
        long gameFinish = System.currentTimeMillis();
        long diff = gameFinish - gameStartTime;

        Game game = new Game();
        game.setCorrectGuesses(correctAnswers);
        game.setDuration(diff);
        game.setPlayer(player);
        if (correctAnswers > player.getHighScore()) {
            player.setHighScore(correctAnswers);
            playerRepository.save(player);
        }
        gameRepository.save(game);
    }
}

// React 70%
// Angular 20%
// Vue 10%

// React 50%
// Angular 40%
// Vue 10%

// 10 arendajat
// arendajad 50% tiimist
// testijad 25% tiimist
// projektijuht, analüütik 20%
// disainer 5%

// 1-2 kuud tasuta, 3-4 kuud miinimum, 5-12 kuud 1000
// 1-1.5a 1500  2a 2000 3a 3000

// 1500 2500
