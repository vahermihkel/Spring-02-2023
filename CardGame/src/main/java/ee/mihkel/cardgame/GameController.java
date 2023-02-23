package ee.mihkel.cardgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
public class GameController {
    private Card card;
    private LocalDateTime roundStartTime;
    private int correctAnswers;
    private int lives;
    private Date gameStartTime;

    private Player player;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("player/{playerName}")
    public String player(@PathVariable String playerName) {
        Optional<Player> playerFound = playerRepository.findById(playerName);
        if (playerFound.isEmpty()) {
            Player newPlayer = new Player(playerName, new Date(), 0);
            player = playerRepository.save(newPlayer);
        } else {
            player = playerFound.get();
        }
        return "OK";
    }

    @GetMapping("start")
    public Card startRound() {
        roundStartTime = LocalDateTime.now();
        if (card == null) {
            correctAnswers = 0;
            lives = 3;
            gameStartTime = new Date();
            card = new Card();
        }
        return card;
    }

    @GetMapping("guess/{userGuess}") // front-end peab tegema kasutajale 3 nuppu, mis saadab backendile lower, higher või equal
    public String makeGuess(@PathVariable String userGuess) {
        // 19.07.54
        // 19.07.45+10 ---> 19.07.55
        LocalDateTime roundStartTimeLater = roundStartTime.plusSeconds(10);
//        System.out.println(roundStartTimeLater);
        LocalDateTime actualTime = LocalDateTime.now();
//        System.out.println(actualTime);
        if (actualTime.isAfter(roundStartTimeLater)) {
            lives--;
            if (lives == 0) {
                card = null;

                Date gameFinish = new Date();
                Date diff = new Date(gameFinish.getTime() - gameStartTime.getTime());

                Game game = new Game(1L,correctAnswers, diff,player);
                gameRepository.save(game);
                return "Game over!";
            }
            return "Not answered in 10 seconds!";
        }
        Card newCard = new Card();
        String response;
        if (userGuess.equals("lower") && card.getValue() > newCard.getValue() ||
                userGuess.equals("higher") && card.getValue() < newCard.getValue() ||
                    userGuess.equals("equal") && card.getValue() == newCard.getValue()) {
            response = "Correct!";
            correctAnswers++;
        } else {
            response = "Wrong!";
            lives--;
            if (lives == 0) {
                card = null;

                Date gameFinish = new Date();
                Date diff = new Date(gameFinish.getTime() - gameStartTime.getTime());

                Game game = new Game(1L,correctAnswers, diff,player);
                gameRepository.save(game);
                return "Game over!";
            }
        }
        card = newCard;
        return response;
    }

//    @GetMapping("new-round")
//    public Card newRound() {
//        return card;
//    }
}
