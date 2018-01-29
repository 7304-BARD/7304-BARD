package baseballRecruitment.jd;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;

import baseballRecruitment.jd.DataLayer.JPGS;

@EActivity(R.layout.activity_top50)
public class Top50 extends AppCompatActivity {

    static final String [] player_keys = {"name", "positions", "year"};
    static final int [] player_views = {R.id.name, R.id.positions, R.id.year};
    static final String [] stat_keys = {"label", "value"};
    static final int [] stat_views = {R.id.label, R.id.value};

    ProgressDialog pdia;

    @ViewById
    ExpandableListView top_50_list;

    @AfterViews
    protected void load() {
        pdia = ProgressDialog.show(this, "Loading Top 50", "Please wait", true, false);
        fetch50();
    }

    @Background
    protected void fetch50() {
        display50(JPGS.get_top50_mapped("2016"));
    }

    @UiThread
    protected void display50(Pair<ArrayList<HashMap<String, String>>, ArrayList<ArrayList<HashMap<String, String>>>> players) {
        top_50_list.setAdapter(new SimpleExpandableListAdapter(this, players.first, R.layout.watchlist_elv_group_view, player_keys, player_views, players.second, R.layout.watchlist_elv_child_view, stat_keys, stat_views));
        pdia.dismiss();
    }
}
