package ee.mihkel.cardgame.database;

import ee.mihkel.cardgame.game.Game;
import ee.mihkel.cardgame.game.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByPlayer(Player player);

    List<Game> findAllByOrderByDuration();

    List<Game> findAllByOrderByCorrectGuessesDesc();
}
