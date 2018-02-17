package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_welcome_screen)
public class WelcomeScreen extends AppCompatActivity {

    public void login(View view) {
        Login_.intent(this).start();
    }

    public void register(View view) {
        Registration_.intent(this).start();
    }
}
