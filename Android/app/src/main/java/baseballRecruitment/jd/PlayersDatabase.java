package baseballRecruitment.jd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={Player.class}, version=1)
public abstract class PlayersDatabase extends RoomDatabase {
    public abstract PlayersDao userDao();
}