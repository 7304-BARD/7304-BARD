package baseballRecruitment.jd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey(autoGenerate=true)
    public int pid;

    public Player(String name, int year, short positions) {
        this.name = name;
        this.year = year;
        this.positions = positions;
    }

    public int year;
    public String name;
    public short positions;

    public static String [] positionStrings = {"P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF"};

    public static String posToString(short positions) {
        String rv = "";
        for (int i = 0; i < 9; i++)
            if ((positions & (1 << i)) != 0)
                rv += "/" + positionStrings[i];
        return rv.length() == 0 ? rv : rv.substring(1);
    }
}
