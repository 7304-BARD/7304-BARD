package baseballRecruitment.jd;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_home_page)
public class HomePage extends AppCompatActivity {

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
       //Tournaments.start();
       startActivityForResult(new Intent(this, Tournaments.class), 0);
   }
}
