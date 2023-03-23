package ee.mihkel.cardgame.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping("player/{playerName}")
    public ResponseEntity<String> player(@PathVariable String playerName) {
        return ResponseEntity.ok().body(gameService.savePlayer(playerName));
    }

    @GetMapping("start")
    public Card startRound() {
        return gameService.getCard();
    }

    @GetMapping("guess/{choice}") // front-end peab tegema kasutajale 3 nuppu, mis saadab backendile lower, higher või equal
    public String makeGuess(@PathVariable String choice) {
        return gameService.userGuess(choice);
    }

}
