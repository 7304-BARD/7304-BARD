package baseballRecruitment.jd;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SimpleExpandableListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
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
    ArrayList<JPGS.Player> players;
    ArrayList<ArrayList<HashMap<String, String>>> details;
    SimpleExpandableListAdapter adapter;

    @ViewById
    ExpandableListView top_50_list;

    @ViewById
    ProgressBar pbar;

    @AfterViews
    protected void load() {
        pdia = ProgressDialog.show(this, "Loading Top 50", "Please wait", true, false);
        fetch50();
    }

    @Background
    protected void fetch50() {
        display50(JPGS.get_top50_basic("2016"));
    }

    @UiThread
    protected void display50(Pair<ArrayList<JPGS.Player>, ArrayList<HashMap<String, String>>> pair) {
        players = pair.first;
        details = new ArrayList(pair.first.size());
        for (int i = 0; i < pair.first.size(); i++)
            details.add(new ArrayList(0));
        adapter = new SimpleExpandableListAdapter(this, pair.second, R.layout.watchlist_elv_group_view, player_keys, player_views, details, R.layout.watchlist_elv_child_view, stat_keys, stat_views);
        top_50_list.setAdapter(adapter);
        pdia.dismiss();
        pbar.setVisibility(View.VISIBLE);
        loadDetails();
    }

    @UiThread
    protected void updateDetails() {
        adapter.notifyDataSetChanged();
        pbar.setVisibility(View.GONE);
    }

    @Background
    protected void loadDetails() {
        try {
            players = JPGS.getPlayers(players);
            for (int i = 0; i < players.size(); i++)
                details.set(i, players.get(i).detailMap());
        } catch (IOException e) {}
        updateDetails();
    }
}
