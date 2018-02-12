package baseballRecruitment.jd;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void watchlist(View view) {
        Watchlist_.intent(this).start();
    }

    public void mycalendar(View view) {
        MyCalendar_.intent(this).start();
    }

    public void top50(View view) {
        startActivity(new Intent(this, Top50_.class));
    }

    public void map(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination_place_id=ChIJv78fsWEE9YgR3Zqua8Olkw0&destination=Russ+Chandler+Stadium")));
    }
}
