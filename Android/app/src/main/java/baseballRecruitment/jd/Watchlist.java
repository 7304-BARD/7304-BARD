package baseballRecruitment.jd;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_watchlist)
public class Watchlist extends AppCompatActivity {

    @ViewById
    ExpandableListView watchlist;

    PlayersDatabase db;

    List<Player> players;

    @AfterViews
    protected void load() {
        db = Room.databaseBuilder(getApplicationContext(), PlayersDatabase.class, "players").fallbackToDestructiveMigration().build();
        watchlist.setOnCreateContextMenuListener(new ExpandableListView.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                final ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) contextMenuInfo;
                if (ExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_GROUP)
                    contextMenu.add("Remove").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            removePlayer(players.get(ExpandableListView.getPackedPositionGroup(info.packedPosition)));
                            return false;
                        }
                    });
            }
        });
        updateWL();
    }

    @Background
    protected void removePlayer(Player p) {
        db.userDao().deletePlayers(p);
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
        players = db.userDao().getWatchlist();
        updateWL_report(ELVMappable.setup(this, Player.player_keys, players));
    }

    @UiThread
    void updateWL_report(ELVMappable.Map elvm) {
        ELVMappable.apply(watchlist, elvm);
    }
}
