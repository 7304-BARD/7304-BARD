package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import baseballRecruitment.jd.DataLayer.ILoginManager;
import baseballRecruitment.jd.DataLayer.MockLoginManager;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    public void login(View view) {
        final String emailString = email.getText().toString();
        final String passwordString = password.getText().toString();

        ILoginManager loginManager = new MockLoginManager(view.getContext());
        if (loginManager.checkCredentials(emailString, passwordString)) {
            HomePage_.intent(this).start();
            finish();
        }
        else {
            password.setError("Invalid email or password.");
        }
    }
}
