package baseballRecruitment.jd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    void login(View view) {
        startActivity(new Intent(this, Login.class));
    }

    void register(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
