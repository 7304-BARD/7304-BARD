package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Top50 extends AppCompatActivity {

    static final String [] player_keys = {"name", "positions", "year"};
    static final int [] player_views = {R.id.name, R.id.positions, R.id.year};
    static final String [] stat_keys = {"label", "value"};
    static final int [] stat_views = {R.id.label, R.id.value};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top50);
        ExpandableListView top50 = findViewById(R.id.top_50_list);
        List<Player> players = RandomData.randomPlayers(50);
        ArrayList<HashMap<String, String>> players_mapped = RandomData.playerMaps(players);
        ArrayList<ArrayList<HashMap<String, String>>> player_stats = RandomData.detailedPlayerMaps(players);
        top50.setAdapter(new SimpleExpandableListAdapter(this, players_mapped, R.layout.watchlist_elv_group_view, player_keys, player_views, player_stats, R.layout.watchlist_elv_child_view, stat_keys, stat_views));

    }
}
