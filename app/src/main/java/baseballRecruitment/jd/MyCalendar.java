package baseballRecruitment.jd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
    }

    public void new_meeting(View view) {
        startActivityForResult(new Intent(this, NewMeeting.class), 0);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
    }
}
