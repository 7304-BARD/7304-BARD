package baseballRecruitment.jd;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import baseballRecruitment.jd.DataLayer.JPGS;

public class Top50 extends AppCompatActivity {

    static final String [] player_keys = {"name", "positions", "year"};
    static final int [] player_views = {R.id.name, R.id.positions, R.id.year};
    static final String [] stat_keys = {"label", "value"};
    static final int [] stat_views = {R.id.label, R.id.value};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top50);
        final ExpandableListView top50 = findViewById(R.id.top_50_list);
        final ProgressDialog pdia = ProgressDialog.show(this, "Loading Top 50", "Please wait", true, false);
        new AsyncTask<Void, Void, JPGS.Pair<ArrayList<HashMap<String, String>>, ArrayList<ArrayList<HashMap<String, String>>>>>() {
            @Override
            protected JPGS.Pair<ArrayList<HashMap<String, String>>, ArrayList<ArrayList<HashMap<String, String>>>> doInBackground(Void... voids) {
                return JPGS.get_top50_mapped("2016");
            }
            protected void onPostExecute(JPGS.Pair<ArrayList<HashMap<String, String>>, ArrayList<ArrayList<HashMap<String, String>>>> players) {
                top50.setAdapter(new SimpleExpandableListAdapter(Top50.this, players.first, R.layout.watchlist_elv_group_view, player_keys, player_views, players.second, R.layout.watchlist_elv_child_view, stat_keys, stat_views));
                pdia.dismiss();
            }
        }.execute();
    }
}
