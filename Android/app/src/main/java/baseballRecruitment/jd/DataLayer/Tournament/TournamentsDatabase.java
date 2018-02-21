package baseballRecruitment.jd.DataLayer.Tournament;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={Tournament.class}, version=1, exportSchema=false)
public abstract class TournamentsDatabase extends RoomDatabase {
    public abstract TournamentsDao userDao();
}
