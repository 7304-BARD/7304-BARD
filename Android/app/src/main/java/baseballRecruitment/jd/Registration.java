package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.arch.persistence.room.Room;
import android.widget.TextView;
import baseballRecruitment.jd.DataLayer.Account.*;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void register(View view) {
        // TODO Encapsulate later
        final String email = ((TextView) findViewById(R.id.email_response)).getText().toString();
        final String username = ((TextView) findViewById(R.id.username_response)).getText().toString();
        final String password = ((TextView) findViewById(R.id.password_response)).getText().toString();
        final String password_confirm = ((TextView) findViewById(R.id.confirm_password)).getText().toString();
        final String organization = ((TextView) findViewById(R.id.org_response)).getText().toString();

        if (!password.equals(password_confirm))
            ; // TODO: admonish user

        Account acc = new Account(email, username, password, organization);
        AccountsDatabase db =
                Room.databaseBuilder(getApplicationContext(), AccountsDatabase.class, "accounts")
                        .allowMainThreadQueries().build();
        db.userDao().insertAccounts(acc);

        // TODO: congratulate user
        finish(); // TODO
    }
}
