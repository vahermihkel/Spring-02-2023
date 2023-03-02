package ee.mihkel.cardgame.database;

import ee.mihkel.cardgame.game.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
