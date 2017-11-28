package baseballRecruitment.jd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PlayersDao {
    @Query("SELECT * from player")
    List<Player> getPlayers();
}
