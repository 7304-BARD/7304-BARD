package baseballRecruitment.jd.DataLayer.Player;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={Player.class}, version=2, exportSchema=false)
public abstract class PlayersDatabase extends RoomDatabase {
    public abstract PlayersDao userDao();
}
