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
        // TODO: Real data
        List<TournamentsData> td = new ArrayList<>(2);
        td.add(new TournamentsData("T64", "2018-06-22", "Alcester"));
        td.add(new TournamentsData("T19", "2018-06-05", "Possum Kingdom"));
        displayTL(ELVMappable.setup(this, TournamentsData.keys, td));
    }

    @UiThread
    protected void displayTL(ELVMappable.Map map) {
        ELVMappable.apply(tournamentsList, map);
        pdia.dismiss();
    }
}
