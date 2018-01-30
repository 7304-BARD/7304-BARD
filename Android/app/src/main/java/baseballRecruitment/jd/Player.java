package baseballRecruitment.jd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey(autoGenerate=true)
    public int pid;

    boolean watchlist;

    public Player(String name, String year, String position, boolean watchlist) {
        this.name = name;
        this.year = year;
        this.position = position;
        this.watchlist = watchlist;
    }

    public String year;
    public String name;
    public String position;
}
