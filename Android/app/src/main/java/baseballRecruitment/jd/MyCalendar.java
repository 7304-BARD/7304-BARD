package baseballRecruitment.jd;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

import baseballRecruitment.jd.DataLayer.Event.Event;
import baseballRecruitment.jd.DataLayer.Event.EventsDatabase;

@EActivity(R.layout.activity_my_calendar)
public class MyCalendar extends AppCompatActivity {

    @ViewById
    WebView webcal;

    @AfterViews
    protected void load() {
        webcal.getSettings().setJavaScriptEnabled(true);
        webcal.addJavascriptInterface(new JSEventsHelper(), "AndroidEvents");
        webcal.loadUrl("file:///android_asset/cal.html");
    }

    @Click
    public void new_meeting() {
        NewMeeting_.intent(this).startForResult(0);
    }

    @OnActivityResult(0)
    protected void onResult(int resultCode, @OnActivityResult.Extra Calendar start,  @OnActivityResult.Extra Calendar end,  @OnActivityResult.Extra String details) {
        if (resultCode == RESULT_OK)
            insertEvent(start, end, details);
    }

    @Background
    protected void insertEvent(Calendar start, Calendar end, String details) {
        Cal.putEvent(this, start, end, details);
        refresh();
    }

    @UiThread
    protected void refresh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            webcal.evaluateJavascript("refresh();", null);
        else
            webcal.loadUrl("javascript:refresh();");
    }

    private class JSEventsHelper {
        @JavascriptInterface
        public String events(long start, long end) {
            return new Gson().toJson(Cal.getEvents(MyCalendar.this, start, end));
        }
    }

    private static class Cal {
        static void putEvent(Context cxt, Calendar start, Calendar end, String details) {
            long startms = start.getTimeInMillis();
            long endms = end.getTimeInMillis();
            Room.databaseBuilder(cxt.getApplicationContext(), EventsDatabase.class, "events").build().userDao().insert(new Event(startms, endms, details));
        }

        static List<Event> getEvents(Context cxt, long start, long end) {
            List<Event> events = Room.databaseBuilder(cxt.getApplicationContext(), EventsDatabase.class, "events").build().userDao().getEventsDuring(start, end);
            Log.w("", new Gson().toJson(events));
            return events;
        }
    }
}
