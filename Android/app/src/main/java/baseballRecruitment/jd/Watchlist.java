package baseballRecruitment.jd;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EActivity(R.layout.activity_watchlist)
public class Watchlist extends AppCompatActivity {

    static final String [] player_keys = {"name", "positions", "year"};
    static final int [] player_views = {R.id.name, R.id.positions, R.id.year};
    static final String [] stat_keys = {"label", "value"};
    static final int [] stat_views = {R.id.label, R.id.value};

    @ViewById
    ExpandableListView watchlist;

    PlayersDatabase db;

    @AfterViews
    protected void load() {
        db = Room.databaseBuilder(getApplicationContext(), PlayersDatabase.class, "players").fallbackToDestructiveMigration().build();
        updateWL();
    }

    @Override
    protected void onActivityResult(int request, int result, Intent intent) {
        if (request == 0 && result == RESULT_OK)
            addPlayer((Player) intent.getSerializableExtra(SearchActivity.extraKeyNewPlayer));
    }

    @Background
    protected void addPlayer(Player p) {
        p.watchlist = true;
        db.userDao().insertPlayers(p);
        updateWL();
    }

    public void newplayer(View view) {
        startActivityForResult(new Intent(this, SearchActivity_.class), 0);
    }

    @Background
    void updateWL() {
        List<Player> players = db.userDao().getWatchlist();
        updateWL_report(Player.playerMaps(players), Player.detailedPlayerMaps(players));
    }

    @UiThread
    void updateWL_report(ArrayList<HashMap<String, String>> players_mapped, ArrayList<ArrayList<HashMap<String, String>>> player_stats) {
        watchlist.setAdapter(new SimpleExpandableListAdapter(this, players_mapped, R.layout.watchlist_elv_group_view, player_keys, player_views, player_stats, R.layout.watchlist_elv_child_view, stat_keys, stat_views));
    }
}
