package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

import java.util.Arrays;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {
    @AfterViews
    protected void load() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            AuthUI.IdpConfig emailConfig = new AuthUI.IdpConfig.EmailBuilder()
              .setRequireName(false).build();
            startActivityForResult(AuthUI.getInstance()
              .createSignInIntentBuilder()
              .setAvailableProviders(Arrays.asList(emailConfig))
              .build(), 0);
          }
        else {
            homepage();
        }
    }

    private void homepage() {
        HomePage_.intent(this).start();
        finish();
    }

    @OnActivityResult(0)
    protected void onResult(int result) {
        if (result == RESULT_OK)
            homepage();
        else
            load();
    }
}
