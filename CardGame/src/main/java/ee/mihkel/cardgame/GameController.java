package ee.mihkel.cardgame;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class GameController {
    private Card card;
    private LocalDateTime roundStartTime;

    @GetMapping("start")
    public Card startRound() {
        roundStartTime = LocalDateTime.now();
        if (card == null) {
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
            return "Not answered in 10 seconds!";
        }
        Card newCard = new Card();
        String response;
        if (userGuess.equals("lower") && card.getValue() > newCard.getValue() ||
                userGuess.equals("higher") && card.getValue() < newCard.getValue() ||
                    userGuess.equals("equal") && card.getValue() == newCard.getValue()) {
            response = "Correct!";
        } else {
            response = "Wrong!";
        }
        card = newCard;
        return response;
    }

//    @GetMapping("new-round")
//    public Card newRound() {
//        return card;
//    }
}
