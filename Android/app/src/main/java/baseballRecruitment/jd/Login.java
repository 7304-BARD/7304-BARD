package baseballRecruitment.jd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import baseballRecruitment.jd.DataLayer.ILoginManager;
import baseballRecruitment.jd.DataLayer.MockLoginManager;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        final String email = ((EditText) findViewById(R.id.email)).getText().toString();
        final String password = ((EditText) findViewById(R.id.password)).getText().toString();

        final boolean quickLogin = email.equalsIgnoreCase("rmoore")
                && password.equalsIgnoreCase("marywas14");

        ILoginManager loginManager = new MockLoginManager(view.getContext());
        if (quickLogin || loginManager.checkCredentials(email, password)) {
            startActivity(new Intent(this, HomePage.class));
            finish();
        }

        else {
            EditText passwordView = findViewById(R.id.password);
            passwordView.setError("Invalid email or password.");
        }
    }
}
