package baseballRecruitment.jd.DataLayer.Account;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={Account.class}, version=1, exportSchema=false)
public abstract class AccountsDatabase extends RoomDatabase {
  public abstract AccountsDao userDao();
}
