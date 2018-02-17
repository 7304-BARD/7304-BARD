package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.arch.persistence.room.Room;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import baseballRecruitment.jd.DataLayer.Account.*;

@EActivity(R.layout.activity_registration)
public class Registration extends AppCompatActivity {

    @ViewById
    TextView email_response;

    @ViewById
    TextView username_response;

    @ViewById
    TextView password_response;

    @ViewById
    TextView confirm_password;

    @ViewById
    TextView org_response;

    public void register(View view) {
        // TODO Encapsulate later
        final String email = email_response.getText().toString();
        final String username = username_response.getText().toString();
        final String password = password_response.getText().toString();
        final String password_confirm = confirm_password.getText().toString();
        final String organization = org_response.getText().toString();

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
