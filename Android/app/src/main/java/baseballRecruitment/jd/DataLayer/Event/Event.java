package baseballRecruitment.jd.DataLayer.Event;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Event {
    @PrimaryKey(autoGenerate=true)
    long id;

    public long start;
    public long end;
    public String title;

    public Event(long start, long end, String title) {
        this.start = start;
        this.end = end;
        this.title = title;
    }
}
