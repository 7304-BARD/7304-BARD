package baseballRecruitment.jd;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import baseballRecruitment.jd.DataLayer.JPGS;

@EActivity(R.layout.activity_tournaments)
public class Tournaments extends AppCompatActivity {

    @ViewById
    ExpandableListView tournamentsList;

    ProgressDialog pdia;

    @AfterViews
    protected void load() {
        pdia = ProgressDialog.show(this, "Loading Data", "Please wait", true, false);
        fetchTL();
    }

    @Background
    protected void fetchTL() {
        displayTL(ELVMappable.setup(this, TournamentsData.keys, JPGS.getTournamentsData()));
    }

    @UiThread
    protected void displayTL(ELVMappable.Map map) {
        map.group_view = R.layout.tournaments_elv_group_view;
        ELVMappable.apply(tournamentsList, map);
        pdia.dismiss();
    }
}
