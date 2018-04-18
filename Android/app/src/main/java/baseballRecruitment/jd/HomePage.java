package baseballRecruitment.jd;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_home_page)
@OptionsMenu(R.menu.hp_options)
public class HomePage extends AppCompatActivity {

    @OptionsItem
    protected void logout() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                Login_.intent(HomePage.this).start();
                finish();
            }
        });
    }

    public void watchlist(View view) {
        Watchlist_.intent(this).start();
    }

    public void mycalendar(View view) {
        MyCalendar_.intent(this).start();
    }

    public void top50(View view) {
        Top50_.intent(this).start();
    }

    public void map(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination_place_id=ChIJv78fsWEE9YgR3Zqua8Olkw0&destination=Russ+Chandler+Stadium")));
    }

   public void tournaments(View view) {
       Tournaments_.intent(this).start();
   }
}
