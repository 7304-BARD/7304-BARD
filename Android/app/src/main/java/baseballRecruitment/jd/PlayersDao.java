package baseballRecruitment.jd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlayersDao {
    @Query("SELECT * from player")
    List<Player> getPlayers();

    @Query("SELECT * from player where watchlist")
    List<Player> getWatchlist();

    @Update
    void updatePlayers(Player... players);

    @Insert
    void insertPlayers(Player... players);

    @Delete
    void deletePlayers(Player... players);
}
