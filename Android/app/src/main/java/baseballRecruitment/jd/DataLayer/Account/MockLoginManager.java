package baseballRecruitment.jd.DataLayer.Account;

/**
 * Created by sergeyscottnall on 11/27/17.
 */

import android.arch.persistence.room.Room;
import android.content.Context;

public class MockLoginManager implements ILoginManager {
    public MockLoginManager(Context context) {
        super();
        this.context = context;
    }

    public boolean checkCredentials(String username, String password) {
        AccountsDatabase db =
                Room.databaseBuilder(context, AccountsDatabase.class, "accounts")
                        .allowMainThreadQueries().build();
        Account[] accounts = db.userDao().lookupAccount(username, password);
        if (accounts.length < 1)
          accounts = db.userDao().lookupAccountEmail(username, password);

        return accounts.length > 0;
    }

    private android.content.Context context;
}
