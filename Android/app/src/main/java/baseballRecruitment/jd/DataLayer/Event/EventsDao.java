package baseballRecruitment.jd.DataLayer.Event;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventsDao {
  @Query("SELECT * FROM event")
  public List<Event> getEvents();

  @Query("SELECT * FROM event WHERE start BETWEEN :start AND :end")
  public List<Event> getEventsDuring(long start, long end);

  @Insert
  public void insert(Event... accounts);
}
