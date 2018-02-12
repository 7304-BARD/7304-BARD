package baseballRecruitment.jd.DataLayer.Event;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={Event.class}, version=1, exportSchema=false)
public abstract class EventsDatabase extends RoomDatabase {
  public abstract EventsDao userDao();
}
