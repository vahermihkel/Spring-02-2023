package ee.mihkel.cardgame.database;

import ee.mihkel.cardgame.game.Game;
import ee.mihkel.cardgame.game.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @GetMapping("players")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("game/{playerId}")
    public List<Game> getPlayerGames(@PathVariable String playerId) {
        Player player = playerRepository.findById(playerId).get();
        return gameRepository.findAllByPlayer(player);
    }

    @GetMapping("games")
    public List<Game> getGamesFromHighestScore() {
//        return gameRepository.findAll(Sort.by(Sort.Direction.DESC, "correctGuesses"));
        return gameRepository.findAllByOrderByCorrectGuessesDesc();
    }
}
