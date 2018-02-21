package baseballRecruitment.jd;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
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

import baseballRecruitment.jd.DataLayer.ELVMappable;
import baseballRecruitment.jd.NetworkLayer.JPGS;
import baseballRecruitment.jd.DataLayer.Player.Player;
import baseballRecruitment.jd.DataLayer.Player.PlayersDatabase;

@EActivity(R.layout.activity_top50)
public class Top50 extends AppCompatActivity {

    ProgressDialog pdia;
    ArrayList<Player> players;
    ArrayList<ArrayList<HashMap<String, String>>> details;
    SimpleExpandableListAdapter adapter;
    PlayersDatabase db;

    @ViewById
    ExpandableListView top_50_list;

    @ViewById
    ProgressBar pbar;

    @AfterViews
    protected void load() {
        pdia = ProgressDialog.show(this, "Loading Top 50", "Please wait", true, false);
        db = Room.databaseBuilder(getApplicationContext(), PlayersDatabase.class, "players").fallbackToDestructiveMigration().build();
        fetch50();
    }

    @Background
    protected void fetch50() {
        players = JPGS.get_top50_basic("2016");
        display50();
    }

    @UiThread
    protected void display50() {
        ELVMappable.Map map = ELVMappable.setup(this, Player.player_keys, players);
        details = map.detailMapped;
        adapter = ELVMappable.apply(top_50_list, map);
        top_50_list.setOnCreateContextMenuListener(new CMListener());
        pdia.dismiss();
        pbar.setVisibility(View.VISIBLE);
        loadDetails();
    }

    @Background
    protected void addPlayerWL(Player player) {
        player.watchlist = true;
        if (!player.populated)
            try {
                player.populate();
            } catch (IOException e) {}
        db.userDao().insertPlayers(player);
    }

    @UiThread
    protected void updateDetails() {
        adapter.notifyDataSetChanged();
        pbar.setVisibility(View.GONE);
    }

    @Background
    protected void loadDetails() {
        for (int i = 0; i < players.size(); i++)
        try {
            players.get(i).populate();
            details.set(i, players.get(i).detailMap());
        } catch (IOException e) {}
        updateDetails();
    }

    private class CMListener implements ExpandableListView.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        ExpandableListView.ExpandableListContextMenuInfo info;

        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            info = (ExpandableListView.ExpandableListContextMenuInfo) contextMenuInfo;
            if (ExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_GROUP)
                contextMenu.add("Add to watchlist").setOnMenuItemClickListener(this);
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            addPlayerWL(players.get(ExpandableListView.getPackedPositionGroup(info.packedPosition)));
            return false;
        }
    }
}
