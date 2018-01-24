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

    public void login(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void register(View view) {
        startActivity(new Intent(this, Registration.class));
    }
}